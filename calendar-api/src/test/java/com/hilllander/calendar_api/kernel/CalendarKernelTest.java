package com.hilllander.calendar_api.kernel;

import com.hilllander.calendar_api.model.AstroDetail;
import com.hilllander.calendar_api.model.MyanmarDate;
import com.hilllander.calendar_api.model.WesternDate;
import com.hilllander.calendar_api.util.DateFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by khunzohn on 10/18/15.
 */
public class CalendarKernelTest {
    CalendarKernel cal;

    @Before
    public void setUp() throws Exception {
        cal = new CalendarKernel();
    }

    @After
    public void tearDown() throws Exception {
        cal = null;
    }

    @Test
    public void testDayFraction() throws Exception {
        List<DayFractionBundle> samples = new ArrayList<>();
        samples.add(new DayFractionBundle(0.0, 12, 0, 0));
        samples.add(new DayFractionBundle(0.0416666665, 13, 0, 0));
        samples.add(new DayFractionBundle(0.177662037, 16, 15, 50));
        samples.add(new DayFractionBundle(0.4583333335, 23, 0, 0));

        for (DayFractionBundle sample : samples) {
            double fraction = sample.getFraction();
            int
                    h = sample.getH(),
                    m = sample.getM(),
                    s = sample.getS();

            assertEquals(fraction, cal.time2DayFraction(h, m, s), 1);
        }
    }

    @Test
    public void testBuddistYear() throws Exception {
        int by = 2559, my = 1377;
        assertEquals(by, cal.getBuddhaYear(my));
    }

    @Test
    public void testIsLeapYearOf1stEra() throws Exception {
        int[] mys = {2, 5, 7, 10, 13, 15, 18, 21, 24, 1025,/*1202*/1063, 1185, 1188};
        for (int my : mys)
            assertTrue(cal.isWatat(my));
    }

    @Test
    public void year1201ShouldBeLeapYear() throws Exception {
        assertTrue(cal.isWatat(1201));
    }

    @Test
    public void year1202ShouldNotBeLeapYear() throws Exception {
        assertTrue(!cal.isWatat(1202));
    }

    @Test
    public void testIsLeapYearOf2ndEra() throws Exception {
        int[] mys = {1266, 1274, 1280, 1285, 1291, 1296, 1301, 1310, 1263, 1269,
                1272, 1277, 1282, 1288, 1293, 1299, 1304, 1307};
        for (int my : mys)
            assertTrue(cal.isWatat(my));
    }

    @Test
    public void year1264ShouldNotBeLeapYear() throws Exception {
        assertTrue(!cal.isWatat(1264));
    }

    @Test
    public void year1263ShouldBeLeapYear() throws Exception {
        assertTrue(cal.isWatat(1263));
    }

    @Test
    public void testIsLeapYearOf3rdEra() throws Exception {
        int[] mys = {1317, 1320,
                1328, 1334, 1336/*1344*/, 1350, 1355, 1361, 1366, 1374, 1377, 1382, 1312,
                1315, 1323, 1326, 1331, 1339, 1342, 1347, 1353, 1358, 1363, 1369,
                1372, 1380};
        for (int my : mys)
            assertTrue(cal.isWatat(my));
    }

    @Test
    public void year1344ShouldBeLeapYear() throws Exception {
        assertTrue(cal.isWatat(1344));
    }

    @Test
    public void year1345ShouldNotBeLeapYear() throws Exception {
        assertTrue(!cal.isWatat(1345));
    }

    @Test
    public void year1377ShouldHaveWasoDay2457235() throws Exception {
        double jd = 2457235;
        int my = 1377;
        assertEquals(jd, cal.getSecondWasoDay(my), 0);
    }

    @Test
    public void testWasoDayOf3Era() throws Exception {

        double wasos[][] = {
                new double[]{2454311, 1369},
                new double[]{2453218, 1366},
                new double[]{2452126, 1363},
                new double[]{2451387, 1361},
                new double[]{2450295, 1358},
                new double[]{2433492, 1312},
                new double[]{2434585, 1315},
                new double[]{2435323, 1317},
                new double[]{2436415, 1320},
                new double[]{2437508, 1323},
                new double[]{2438601, 1326},
                new double[]{2439339, 1328},
                new double[]{2440432, 1331},
                new double[]{2441524, 1334},
                new double[]{2442262, 1336},
                new double[]{2443355, 1339},
                new double[]{2444448, 1342},
                new double[]{2448464, 1353},
                new double[]{2449202, 1355}
        };
        for (double[] waso : wasos)
            assertEquals(waso[0], cal.getSecondWasoDay((int) waso[1]), 0);
    }

    @Test
    public void testWasoDayOf2Era() throws Exception {

        double wasos[][] = {
                new double[]{2432753, 1310},
                new double[]{2431661, 1307},
                new double[]{2430568, 1304},
                new double[]{2429475, 1301},
                new double[]{2428737, 1299},
                new double[]{2426552, 1293},
                new double[]{2425813, 1291},
                new double[]{2029159, 205},
                new double[]{2044131, 246},
                new double[]{2163197, 572},
                new double[]{2192050, 651},
                new double[]{2193881, 656},
                new double[]{2199728, 672},
                new double[]{2220547, 729},
                new double[]{2234425, 767},
                new double[]{2251228, 813},
                new double[]{2264369, 849},
                new double[]{2265107, 851},
                new double[]{2266200, 854},
                new double[]{2333766, 1039}
        };
        for (double[] waso : wasos)
            assertEquals(waso[0], cal.getSecondWasoDay((int) waso[1]), 0);
    }

    @Test
    public void testBigWatat() throws Exception {
        int bigWatats[] = {1263, 1269, 1277, 1282, 1288, 1293, 1299, 1304, 1307, 1312,
                1315, 1323, 1326, 1331, 1339, 1342, 1347, 1353, 1358, 1377, 1363, 1369,
                1372};
        for (int my : bigWatats)
            assertEquals(2, cal.checkMYearType(my));
    }

    @Test
    public void year1299ShouldBeBigWatat() throws Exception {
        assertEquals(2, cal.checkMYearType(1299));
    }

    @Test
    public void year1272ShouldBeBigWatat() throws Exception {
        assertEquals(2, cal.checkMYearType(1272));
    }

    @Test
    public void testSmallWatat() throws Exception {
        int smallWatat[] = {1266, 1274, 1280, 1285, 1291, 1296, 1301, 1310, 1317, 1320,
                1328, 1334, 1336, 1344, 1350, 1355, 1361, 1366, 1374/*, 1377*/, 1382, 1380};
        for (int my : smallWatat)
            assertEquals(1, cal.checkMYearType(my));
    }

    @Test
    public void testFirstTagu() throws Exception {
        int tagus[][] = {{2456010, 1374},
                {2455656, 1373},
                {2451639, 1362}
        };
        for (int[] tago : tagus)
            assertEquals(tago[0], cal.getFirstTagu(tago[1]), 0);
    }

    @Test
    public void testJDate2MyanmarDate() throws Exception {
        int year = 1362, month = 9, day = 12;
        double jd = 2451886;
        String expected = DateFormatter.formatMDate(year, month, day);
        MyanmarDate md = cal.J2M(jd);
        String actual = DateFormatter.formatMDate(md.getYear(), md.getMonth(), md.getWanWaxDay());
        assertEquals(expected, actual);
    }

    //    int mYear,int mMonth,int mType,int mStatus,int day
    @Test
    public void testMDate2JDate() throws Exception {
        double expected = 2448360;
        int
                mYear = 1352,
                mMonth = 2,
                mType = 1,
                mStatus = 0,
                day = 1;
        assertEquals(expected, cal.M2J(mYear, mMonth, mType, mStatus, day), 0);

    }

    @Test
    public void testW2J() throws Exception {
        double expected = 2457180;
        int
                year = 2015,
                month = 6,
                day = 6,
                calType = 1;
        assertEquals(expected, cal.W2J(year, month, day, calType), 0);
    }

    @Test
    public void testJ2W() throws Exception {
        int
                y = 2015,
                m = 6,
                d = 6,
                h = 12,
                n = 0,
                s = 0;
        double jd = 2457180;
        int calType = 1;
        String expected = DateFormatter.format(WesternDate.buildDate()
                .setYear(y).setMonth(m).setDay(d).setHour(h).setMin(n).setSec(s));
        String actual = DateFormatter.format(cal.J2W(jd, calType));
        assertEquals(expected, actual);
    }

    @Test
    public void testCheckAstroDay() throws Exception {
        int
                month = 6,
                monthLength = 30,
                dayOfMonth = 24,
                weekday = 4;
        int
                amyeittasote = 0,
                mahayatkyan = 0,
                nagahle = 2,
                nagapor = 0,
                pyathada = 1,
                sabbath = 0,
                sabbatheve = 0,
                shanyat = 0,
                thamanyo = 1,
                thamaphyu = 0,
                warameittugyi = 1,
                warameittunge = 1,
                yatpote = 1,
                yatyotema = 0,
                yatyaza = 0;

        AstroDetail expectedAs = AstroDetail.buildDate()
                .setAmyeittasote(amyeittasote)
                .setMahayatkyan(mahayatkyan)
                .setNagahle(nagahle)
                .setNagapor(nagapor)
                .setPyathada(pyathada)
                .setSabbath(sabbath)
                .setSabbatheve(sabbatheve)
                .setShanyat(shanyat)
                .setThamanyo(thamanyo)
                .setThamaphyu(thamaphyu)
                .setWarameittugyi(warameittugyi)
                .setWarameittunge(warameittunge)
                .setYatpote(yatpote)
                .setYatyotema(yatyotema)
                .setYatyaza(yatyaza);
        String expected = DateFormatter.formatAstroDetail(expectedAs);
        String actual = DateFormatter.formatAstroDetail(cal.checkAstroDetail(month, monthLength, dayOfMonth, weekday));
        assertEquals(expected, actual);
    }


    private class DayFractionBundle {
        private double fraction;
        private int h, m, s;

        DayFractionBundle(double fraction, int h, int m, int s) {
            this.fraction = fraction;
            this.h = h;
            this.m = m;
            this.s = s;
        }

        public double getFraction() {
            return fraction;
        }

        public int getH() {
            return h;
        }

        public int getM() {
            return m;
        }

        public int getS() {
            return s;
        }
    }
}