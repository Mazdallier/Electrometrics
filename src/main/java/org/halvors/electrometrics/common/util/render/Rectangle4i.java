package org.halvors.electrometrics.common.util.render;

public class Rectangle4i {
    private int x;
    private int y;
    private int w;
    private int h;

	public Rectangle4i() {

	}

	public Rectangle4i(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

    public int getX1() {
        return x;
    }

    public int getY1() {
        return y;
    }

    public int getX2() {
        return x + w - 1;
    }

    public int getY2() {
        return y + h - 1;
    }


	public int x1() {
		return x;
	}

	public int y1() {
		return y;
	}

	public void set(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public Rectangle4i offset(int dx, int dy) {
		x += dx;
		y += dy;

		return this;
	}

	public Rectangle4i include(int px, int py) {
		if (px < x) {
			expand(px - x, 0);
		}

		if (px >= x + w) {
			expand(px - x - w + 1, 0);
		}

		if (py < y) {
			expand(0, py - y);
		}

		if (py >= y + h) {
			expand(0, py - y - h + 1);
		}

		return this;
	}

	public Rectangle4i include(Rectangle4i r) {
		include(r.x, r.y);

		return include(r.getX2(), r.getY2());
	}

	public Rectangle4i expand(int px, int py) {
		if (px > 0) {
			w += px;
		} else {
			x += px;
			w -= px;
		}

		if (py > 0) {
			h += py;
		} else {
			y += py;
			h -= py;
		}

		return this;
	}

	public boolean contains(int px, int py) {
		return x <= px && px < x + w && y <= py && py < y + h;
	}

	public boolean intersects(Rectangle4i r) {
		return r.x + r.w > x &&
				r.x < x + w &&
				r.y + r.h > y &&
				r.y < y + h;
	}

	public int area() {
		return w * h;
	}
}
