package my.org.site.updater.model;

import java.io.File;

public interface ProgressBar {

      int ProgressBarSize = 0;
      double ProgressBarValueNow = 0;
      int ProgressBarPart = 0;
      boolean ProgressBarChecked = false;


    static double roundToMegabytes(int vel) {
            double s = vel / 1024;
            return (s / 1024);
    }

    static int percentageOfProgress(int size, double valueNow) {

        double m = valueNow / size;
        int percent = (int) (m * 100);

        if(percent >= 100){
            return 100;
        }else {
            return  percent;
        }

    }

    static boolean setChecked(File file, int connectionSize ) {
        if(file.length() == connectionSize){
            return true;
        } else {
            return false;
        }
    }
}
