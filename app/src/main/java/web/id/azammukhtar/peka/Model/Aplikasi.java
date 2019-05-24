package web.id.azammukhtar.peka.Model;

import android.graphics.drawable.Drawable;

public class Aplikasi {
    private String namaAplikasi;
    private Drawable iconAplikasi;
    private int locked;
    private String packageName;

    public Aplikasi(String namaAplikasi, Drawable iconAplikasi, int locked, String packageName) {
        this.namaAplikasi = namaAplikasi;
        this.iconAplikasi = iconAplikasi;
        this.locked = locked;
        this.packageName = packageName;
    }

    public String getNamaAplikasi() {
        return namaAplikasi;
    }

    public void setNamaAplikasi(String namaAplikasi) {
        this.namaAplikasi = namaAplikasi;
    }

    public Drawable getIconAplikasi() {
        return iconAplikasi;
    }

    public void setIconAplikasi(Drawable iconAplikasi) {
        this.iconAplikasi = iconAplikasi;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
