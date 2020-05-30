package my.org.site.updater.model;

import java.io.File;

public interface ProgressBar {

      int ProgressBarSize = 0;
      double ProgressBarValueNow = 0;
      int ProgressBarPart = 0;
      boolean ProgressBarChecked = false;


    static double roundToMegabytes(int size) {
        double s = size / 1024;
        return  (s/1024);
    }

    static int percentageOfProgress(int size, double valueNow) {
        double m = valueNow / size;
        return  (int) (m * 100);
    }

    static boolean setChecked(File file, int connectionSize ) {
        if(file.length() == connectionSize){
            return true;
        } else {
            return false;
        }
    }
}
