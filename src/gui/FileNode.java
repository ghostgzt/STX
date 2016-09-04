package gui;

public class FileNode {

    public String show[];
    public int[] image;
    private static String temps;
    public final String title;
    private static int tempi;
    public int zhs, max, hs = 1, dinwei = 1;

    public void setShow(String[] show) {
        this.show = show;
        zhs = show.length;
    }

    public static void sort(String show[], int image[], int low, int high) {
        if (high - low < 1) {
            return;
        }
        temps = show[low];
        tempi = image[low];
        int a = low, b = high;
        String pivotkey = show[low].toLowerCase();
        while (low < high) {
            while (low < high && show[high].toLowerCase().compareTo(pivotkey) >= 0) {
                high--;
            }
            show[low] = show[high];
            image[low] = image[high];
            while (low < high && show[low].toLowerCase().compareTo(pivotkey) <= 0) {
                low++;
            }
            show[high] = show[low];
            image[high] = image[low];
        }
        show[low] = temps;
        image[low] = tempi;
        sort(show, image, a, low);
        sort(show, image, low + 1, b);
    }

    void delete(int n) {
        System.arraycopy(show, n + 1, show, n, zhs - n);
        System.arraycopy(image, n + 1, image, n, zhs - n);
        zhs--;
    }

    void trim() {
        if (show.length - zhs >= 10) {
            String d[] = new String[zhs + 10];
            int t[] = new int[zhs + 10];
            max = zhs + 9;
            System.arraycopy(show, 0, d, 0, zhs);
            System.arraycopy(image, 0, t, 0, zhs);
            show = d;
            image = t;
        }
    }

    public void append(String na, int n) {
        show[zhs] = na;
        image[zhs++] = n + 1;
        if (zhs > max) {
            String d[] = new String[max + 101];
            int t[] = new int[max + 101];
            max += 100;
            System.arraycopy(show, 0, d, 0, zhs);
            System.arraycopy(image, 0, t, 0, zhs);
            show = d;
            image = t;
        }
    }

    public boolean have(String name) {
        for (int i = 1; i < zhs; i++) {
            if (name.equals(show[i])) {
                return true;
            }
        }
        return false;
    }

    void insert(String s) {
        int i = 1;
        while (i < zhs && image[i] == 6) {
            i++;
        }
        if ((i < zhs - 1) || (i == 1 && zhs > 1)) {
            append(show[i], image[i] - 1);
            show[i] = s;
            image[i] = 6;
        } else {
            append(s, 5);
        }
    }

    public FileNode(String name) {
        max = 99;
        title = name;
        show = new String[100];
        image = new int[100];
    }

    public FileNode(String name, String[] show, int[] img) {
        title = name;
        this.show = show;
        zhs = show.length;
        max = zhs - 1;
        image = img;
    }

    public void del(String s) {
        for (int i = 1; i < zhs; i++) {
            if (s.equals(show[i])) {
                delete(i);
                break;
            }
        }
    }
}
