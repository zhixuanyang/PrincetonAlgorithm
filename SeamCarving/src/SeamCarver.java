import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;
    private boolean isVertical = true;
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }
        this.picture = new Picture(picture);
        this.width = picture.width();
        this.height = picture.height();
    }

    public Picture picture() {
        return picture;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public double energy(int x, int y) {
        if (x >= width || x < 0 || y >= height || y < 0) {
            throw new IllegalArgumentException();
        }
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return 1000.0;
        }
        Color up, down, left, right;
        if (isVertical) {
            up = y > 0 ? picture.get(x, y - 1) : picture.get(x, height - 1);
            down = y < height - 1 ? picture.get(x, y + 1) : picture.get(x, 0);
            left = x > 0 ? picture.get(x - 1, y) : picture.get(width - 1, y);
            right = x < width - 1 ? picture.get(x + 1, y) : picture.get(0, y);
        } else {
            up = x > 0 ? picture.get(x - 1, y) : picture.get(height - 1, y);
            down = x < height - 1 ? picture.get(x + 1, y) : picture.get(0, y);
            left = y > 0 ? picture.get(x, y - 1) : picture.get(x, width - 1);
            right = y < width - 1 ? picture.get(x, y + 1) : picture.get(x, 0);
        }
        int rx = left.getRed() - right.getRed();
        int gx = left.getGreen() - right.getGreen();
        int bx = left.getBlue() - right.getBlue();
        int ry = up.getRed() - down.getRed();
        int gy = up.getGreen() - down.getGreen();
        int by = up.getBlue() - down.getBlue();
        return Math.sqrt(rx * rx + gx * gx + bx * bx + ry * ry + gy * gy + by * by);
    }

    public int[] findHorizontalSeam() {
        isVertical = false;
        swap();
        int[] res = findVerticalSeam();
        swap();
        isVertical = true;
        return res;
    }

    private void swap() {
        int temp = width;
        width = height;
        height = temp;
    }

    public int[] findVerticalSeam() {
        int[][] path = new int[width][height];
        double[][] cost = new double[width][height];
        for (int i = 0; i < width; i++) {
            double e = isVertical ? energy(i, 0) : energy(0, i);
            cost[i][0] = e;
            path[i][height - 1] = i;
        }

        for (int j = 1; j < height; j++) {
            for (int i = 0; i < width; i++) {
                double e = isVertical ? energy(i, j) : energy(j, i);
                cost[i][j] = e + getMinCost(i, j, path, cost);
            }
        }

        int[] res = new int[height];
        double min = Double.MAX_VALUE;
        int minPos = 0;
        for (int i = 0; i < width; i++) {
            if (cost[i][height - 1] < min) {
                min = cost[i][height - 1];
                minPos = i;
            }
        }

        for (int j = height - 1; j >= 0; j--) {
            res[j] = path[minPos][j];
            minPos = res[j];
        }
        return res;
    }

    private double getMinCost(int i, int j, int[][] path, double[][] cost) {
        double[] v = new double[3];
        v[1] = cost[i][j - 1];
        if (i > 0) {
            v[0] = cost[i - 1][j - 1];
        } else {
            v[0] = Double.MAX_VALUE;
        }
        if (i < width - 1) {
            v[2] = cost[i + 1][j - 1];
        } else {
            v[2] = Double.MAX_VALUE;
        }
        double res = Double.MAX_VALUE;
        int pos = 0;
        for (int m = 0; m < 3; m++) {
            if (v[m] < res) {
                res = v[m];
                pos = m;
            }
        }
        path[i][j - 1] = pos + i - 1;
        return res;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != width()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > height() - 1) {
                throw new java.lang.IllegalArgumentException();
            }
            if (i != seam.length - 1 && (seam[i] - seam[i + 1] > 1 || seam[i] - seam[i + 1] < -1)) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        if (height() <= 1) {
            throw new java.lang.IllegalArgumentException();
        }
        removeHorizontalSeam(picture, seam);
    }

    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != height()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > width() - 1) {
                throw new java.lang.IllegalArgumentException();
            }
            if (i != seam.length - 1 && (seam[i] - seam[i + 1] > 1 || seam[i] - seam[i + 1] < -1)) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        if (width() <= 1) {
            throw new java.lang.IllegalArgumentException();
        }
        removeVerticalSeam(picture, seam);
    }
    private Picture removeHorizontalSeam(Picture var0, int[] var1) {
        if (var1 == null) {
            throw new NullPointerException("Input seam array cannot be null.");
        } else if (var0.width() == 1) {
            throw new IllegalArgumentException("Image width is 1.");
        } else if (var1.length != var0.width()) {
            throw new IllegalArgumentException("Seam length does not match image width.");
        } else {
            for (int var2 = 0; var2 < var1.length - 2; ++var2) {
                if (Math.abs(var1[var2] - var1[var2 + 1]) > 1) {
                    throw new IllegalArgumentException("Invalid seam, "
                            + "consecutive vertical indices are greater than one apart.");
                }
            }

            Picture var5 = new Picture(var0.width(), var0.height() - 1);

            for (int var3 = 0; var3 < var0.width(); ++var3) {
                int var4;
                for (var4 = 0; var4 < var1[var3]; ++var4) {
                    var5.set(var3, var4, var0.get(var3, var4));
                }

                for (var4 = var1[var3] + 1; var4 < var0.height(); ++var4) {
                    var5.set(var3, var4 - 1, var0.get(var3, var4));
                }
            }
            return var5;
        }
    }

    private Picture removeVerticalSeam(Picture var0, int[] var1) {
        if (var1 == null) {
            throw new NullPointerException("Input seam array cannot be null.");
        } else if (var0.height() == 1) {
            throw new IllegalArgumentException("Image height is 1.");
        } else if (var1.length != var0.height()) {
            throw new IllegalArgumentException("Seam length does not match image height.");
        } else {
            for (int var2 = 0; var2 < var1.length - 2; ++var2) {
                if (Math.abs(var1[var2] - var1[var2 + 1]) > 1) {
                    throw new IllegalArgumentException("Invalid seam, "
                            + "consecutive horizontal indices are greater than one apart.");
                }
            }

            Picture var5 = new Picture(var0.width() - 1, var0.height());

            for (int var3 = 0; var3 < var0.height(); ++var3) {
                int var4;
                for (var4 = 0; var4 < var1[var3]; ++var4) {
                    var5.set(var4, var3, var0.get(var4, var3));
                }

                for (var4 = var1[var3] + 1; var4 < var0.width(); ++var4) {
                    var5.set(var4 - 1, var3, var0.get(var4, var3));
                }
            }
            return var5;
        }
    }
}
