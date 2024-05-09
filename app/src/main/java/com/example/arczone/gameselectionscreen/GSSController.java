package com.example.arczone.gameselectionscreen;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class GSSController {
    public static class CarouselPagerAdapter extends FragmentStateAdapter {

        protected CarouselPagerAdapter(AppCompatActivity activity){
            super(activity);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new SnakePageFragment();
                case 1:
                    return new PongPageFragment();
                case 2:
                    return new SpaceInvaderPageFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    public static class DepthPageTransformer implements ViewPager2.PageTransformer{

        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(@NonNull View page, float position) {
            int pageWidth = page.getWidth();


            if(position < -1) page.setAlpha(0f);
            else if(position <= 0){
                page.setAlpha(1f);
                page.setTranslationX(0f);
                page.setScaleX(1f);
                page.setScaleY(1f);
            }
            else if(position <= 1){
                page.setAlpha(1 - position);

                page.setTranslationX(pageWidth * -position);

                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            }
            else page.setAlpha(0f);
        }
    }
}
