package solaruvi;

import java.util.Scanner;

public class MainActivity {

    public static double upYesterday;
    public static double upToday;

    public static void main(String[] args) {

        MyDate myDate = new MyDate();
        int year = myDate.year;
        int month = myDate.month;
        int day = myDate.day;
        Julian julian = new Julian(year, month, day);
        double T = julian.noonT; // T at noon

        int jatka = 1;

        while (jatka == 1) {
            System.out.println("Jatka, paina 1 ");
            Scanner annaLuku = new Scanner(System.in);
            jatka = annaLuku.nextInt();

            if (jatka == 1) {
                System.out.println("1: Anna sijainti  2:sunToday ");
                Scanner input = new Scanner(System.in);
                int valinta = input.nextInt();
                switch (valinta) {

                    case 1:
                        System.out.println("Anna pituuspiiri [asteina] ");
                        Scanner lon = new Scanner(System.in);
                        double longitude = lon.nextDouble();
                        Location.longitude = longitude;
                        
                        System.out.println("Anna leveyspiiri [asteina] ");
                        Scanner lat = new Scanner(System.in);
                        double latitude = lat.nextDouble();
                        Location.latitude = latitude;
                        
                        break;
                    case 2:
                        T = julian.noonT; // T at noon
                        sunNow(T);
                        sunMaxToday(T);
                        break;
                }
            }
        }
    }

    public static void sunNow(double T) {
        
        double latitudeDeg = Location.latitude;
        double longitudeDeg = Location.longitude;

        // calculate sun elements
        ElementsSun elementsSun = new ElementsSun(T);
        double deltaSunDeg = elementsSun.deltaSunDeg;
        double alfaSunDeg = elementsSun.alfaSunDeg;

        StellarTime timeStellar = new StellarTime(T, longitudeDeg);
        double timeStellarLocalDeg = timeStellar.stellarTimeLocalDeg;
        double timeStellarNoon = timeStellar.stellarTimeNoonDeg;

        // Sun position parameters
        SunPosition positionSun = new SunPosition(alfaSunDeg, deltaSunDeg, timeStellarLocalDeg, latitudeDeg);
        double currentSunElevationDeg = positionSun.currentElevationDeg;
        double sunAzimuthDeg = positionSun.currentAzimuthDeg;
        String sunAzimuthString = positionSun.currentAzimuthString;
        double maxSunElevationDeg = positionSun.maxElevationDeg;

        //SolarPower powerSolar = new SolarPower(latitudeDeg, T, currentSunElevationDeg, maxSunElevationDeg);
        South south = new South(alfaSunDeg, timeStellarNoon);
        double timeSouth = south.timeSouth;

        SolarCalculations calcSolar = new SolarCalculations(deltaSunDeg, latitudeDeg, currentSunElevationDeg, maxSunElevationDeg, timeSouth);
        //double sunPowerNow = powerSolar.currentSunPower;
        double sunUvi = calcSolar.UvIndex;

        System.out.println("Sun current elevation: " + currentSunElevationDeg);
        System.out.println("Sun current azimuth: " + sunAzimuthDeg);
        System.out.println("Sun current azimuth: " + sunAzimuthString);
        //System.out.println("Sun current Power [W/m2]: " + sunPowerNow);
        System.out.println("UVI now: " + sunUvi);
    }

    public static void sunMaxToday(double T) {
        
        double latitudeDeg = Location.latitude;
        double longitudeDeg = Location.longitude;

        // calculate sun elements
        ElementsSun elementsSun = new ElementsSun(T);
        double deltaSunDeg = elementsSun.deltaSunDeg;
        double alfaSunDeg = elementsSun.alfaSunDeg;

        StellarTime timeStellar = new StellarTime(T, longitudeDeg);
        double timeStellarLocalDeg = timeStellar.stellarTimeLocalDeg;
        double timeStellarNoon = timeStellar.stellarTimeNoonDeg;

        // Sun position parameters
        SunPosition positionSun = new SunPosition(alfaSunDeg, deltaSunDeg, timeStellarLocalDeg, latitudeDeg);
        double currentSunElevationDeg = positionSun.currentElevationDeg;
        double maxSunElevationDeg = positionSun.maxElevationDeg;

        South south = new South(alfaSunDeg, timeStellarNoon);
        //Rize rize = new Rize(alfaSunDeg, deltaSunDeg, latitudeDeg, timeStellarNoon);
        //Set set = new Set(alfaSunDeg, deltaSunDeg, latitudeDeg, timeStellarNoon);
        //Up up = new Up(alfaSunDeg, deltaSunDeg, latitudeDeg, timeStellarNoon, currentSunElevationDeg);

        double timeSouth = south.timeSouth;
        //upToday = up.timeUp;

        //System.out.println("Time rize: " + rize.timeRizeString);
        //System.out.println("Time south: " + south.timeSouthString);
        //System.out.println("Time set: " + set.timeSetHorizonString);
        //System.out.println("Time up: " + up.timeUpString);

        SolarCalculations calcSolar = new SolarCalculations(deltaSunDeg, latitudeDeg, currentSunElevationDeg, maxSunElevationDeg, timeSouth);
        double sunUviMax = calcSolar.UvIndexMax;

        System.out.println("UVI max today: " + sunUviMax);
        System.out.println("Sun max elevation today: " + maxSunElevationDeg);
    }

}
