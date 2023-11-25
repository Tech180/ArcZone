package com.cpre388.arczone;

import android.graphics.RectF;

public class Brick {
    private RectF rec;
    private boolean Visible;

    public Brick(int r, int c, int num, int Xscreen, int Yscreen) {
        int w = Xscreen / 90;
        int h = Yscreen / 40;
        Visible = true;
        int BPad = 1;
        int Spad = Xscreen / 9;
        int height = Yscreen - (Yscreen / 8 * 2);
        rec = new RectF(c * w + BPad + (Spad * num) + Spad + Spad * num, r * h + BPad + height, c * w + w - BPad + (Spad * num) + Spad + Spad * num, r * h + h - BPad + height);
    }

    public RectF getRec(){
        return this.rec;
    }
    public void setInVisible(){
        Visible = false;
    }
    public boolean getVisiblility(){
        return Visible;
    }
}
