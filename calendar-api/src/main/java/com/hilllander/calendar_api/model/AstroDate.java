/*
 * Copyright (c) The code is developed  by Hilllander. You can modify or use some or the whole piece of code with no limitation. Feel free to do whatever you favour.Good luck!Hilllander team.
 */

package com.hilllander.calendar_api.model;

/**
 * Custom date that bundles all astrological info.
 * <p/>
 * Created by khunzohn on 10/25/15.
 */
public class AstroDate {
    private static AstroDate date;
    private int sabbath = 0;
    private int sabbatheve = 0;
    private int yatyaza = 0;
    private int pyathada = 0;
    private int thamanyo = 0;
    private int amyeittasote = 0;
    private int warameittugyi = 0;
    private int warameittunge = 0;
    private int yatpote = 0;
    private int thamaphyu = 0;
    private int nagapor = 0;
    private int yatyotema = 0;
    private int mahayatkyan = 0;
    private int shanyat = 0;
    private int nagahle;

    private AstroDate() {
        //prevent initializing by constructor
    }

    /**
     * Build date that will be used to store
     * astrological info by using its object's
     * methods' chains.
     *
     * @return AstroDate's static object
     */
    public static AstroDate buildDate() {
        if (date == null)
            date = new AstroDate();
        return date;
    }

    /**
     * get sabbath day if exist
     *
     * @return flag [0 : false, 1 : true]
     */
    public int getSabbath() {
        return sabbath;
    }

    /**
     * set sabbath day
     *
     * @param sabbath [0 : false, 1 : true]
     * @return itself for method chaining
     */
    public AstroDate setSabbath(int sabbath) {
        this.sabbath = sabbath;
        return this;
    }

    /**
     * get sabbatheve if exist
     *
     * @return flag [0 : false, 1 : true]
     */
    public int getSabbatheve() {
        return sabbatheve;
    }

    /**
     * set sabbatheve
     *
     * @param sabbatheve [0 : false, 1 : true]
     * @return itself for method chaining
     */
    public AstroDate setSabbatheve(int sabbatheve) {
        this.sabbatheve = sabbatheve;
        return this;
    }

    /**
     * get yatyaza if exist
     *
     * @return flag [0 : false, 1 : true]
     */
    public int getYatyaza() {
        return yatyaza;
    }

    /**
     * set yatyaza
     *
     * @param yatyaza [0 : false, 1 : true]
     * @return itself for method chaining
     */
    public AstroDate setYatyaza(int yatyaza) {
        this.yatyaza = yatyaza;
        return this;
    }

    /**
     * get yatyaza if exist
     *
     * @return flag [0 : false, 1 : pyathada, 2 : afternoon pyathada]
     */
    public int getPyathada() {
        return pyathada;
    }

    /**
     * set pyathada
     *
     * @param pyathada [0 : false, 1 : pyathada, 2 : afternoon pyathada]
     * @return itself for method chaining
     */

    public AstroDate setPyathada(int pyathada) {
        this.pyathada = pyathada;
        return this;
    }

    /**
     * get thamanyo if exist
     *
     * @return flag [0 : false, 1 : true]
     */
    public int getThamanyo() {
        return thamanyo;
    }

    /**
     * set thamanyo
     *
     * @param thamanyo [0 : false, 1 : true]
     * @return itself for method chaining
     */
    public AstroDate setThamanyo(int thamanyo) {
        this.thamanyo = thamanyo;
        return this;
    }

    /**
     * get amyeittasote if exist
     *
     * @return flag [0 : false, 1 : true]
     */
    public int getAmyeittasote() {
        return amyeittasote;
    }

    /**
     * set amyeittasote
     *
     * @param amyeittasote [0 : false, 1 : true]
     * @return itself for method chaining
     */
    public AstroDate setAmyeittasote(int amyeittasote) {
        this.amyeittasote = amyeittasote;
        return this;
    }

    /**
     * get warameittugyi if exist
     *
     * @return flag [0 : false, 1 : true]
     */
    public int getWarameittugyi() {
        return warameittugyi;
    }

    /**
     * set warameittugyi
     *
     * @param warameittugyi [0 : false, 1 : true]
     * @return itself for method chaining
     */
    public AstroDate setWarameittugyi(int warameittugyi) {
        this.warameittugyi = warameittugyi;
        return this;
    }

    /**
     * get warameittunge if exist
     *
     * @return flag [0 : false, 1 : true]
     */
    public int getWarameittunge() {
        return warameittunge;
    }

    /**
     * set warameittunge
     *
     * @param warameittunge [0 : false, 1 : true]
     * @return itself for method chaining
     */
    public AstroDate setWarameittunge(int warameittunge) {
        this.warameittunge = warameittunge;
        return this;
    }

    /**
     * get yatpote if exist
     *
     * @return flag [0 : false, 1 : true]
     */
    public int getYatpote() {
        return yatpote;
    }

    /**
     * set yatpote
     *
     * @param yatpote [0 : false, 1 : true]
     * @return itself for method chaining
     */
    public AstroDate setYatpote(int yatpote) {
        this.yatpote = yatpote;
        return this;
    }

    /**
     * get thamaphyu if exist
     *
     * @return flag [0 : false, 1 : true]
     */
    public int getThamaphyu() {
        return thamaphyu;
    }

    /**
     * set thamaphyu
     *
     * @param thamaphyu [0 : false, 1 : true]
     * @return itself for method chaining
     */
    public AstroDate setThamaphyu(int thamaphyu) {
        this.thamaphyu = thamaphyu;
        return this;
    }

    /**
     * get nagapor if exist
     *
     * @return flag [0 : false, 1 : true]
     */
    public int getNagapor() {
        return nagapor;
    }

    /**
     * set nagapor
     *
     * @param nagapor [0 : false, 1 : true]
     * @return itself for method chaining
     */
    public AstroDate setNagapor(int nagapor) {
        this.nagapor = nagapor;
        return this;
    }

    /**
     * get yatyotema if exist
     *
     * @return flag [0 : false, 1 : true]
     */
    public int getYatyotema() {
        return yatyotema;
    }

    /**
     * set yatyotema
     *
     * @param yatyotema [0 : false, 1 : true]
     * @return itself for method chaining
     */
    public AstroDate setYatyotema(int yatyotema) {
        this.yatyotema = yatyotema;
        return this;
    }

    /**
     * get mahayatkyan if exist
     *
     * @return flag [0 : false, 1 : true]
     */
    public int getMahayatkyan() {
        return mahayatkyan;
    }

    /**
     * set mahayatkyan
     *
     * @param mahayatkyan [0 : false, 1 : true]
     * @return itself for method chaining
     */
    public AstroDate setMahayatkyan(int mahayatkyan) {
        this.mahayatkyan = mahayatkyan;
        return this;
    }

    /**
     * get shanyat if exist
     *
     * @return flag [0 : false, 1 : true]
     */
    public int getShanyat() {
        return shanyat;
    }

    /**
     * set shanyat
     *
     * @param shanyat [0 : false, 1 : true]
     * @return itself for method chaining
     */
    public AstroDate setShanyat(int shanyat) {
        this.shanyat = shanyat;
        return this;
    }

    /**
     * get nagahle
     *
     * @return nagahle [0: west, 1: north, 2: east, 3: south]
     */
    public int getNagahle() {
        return nagahle;
    }

    /**
     * set nagahle
     *
     * @param nagahle [0: west, 1: north, 2: east, 3: south]
     * @return itself for method chaining
     */
    public AstroDate setNagahle(int nagahle) {
        this.nagahle = nagahle;
        return this;
    }
}
