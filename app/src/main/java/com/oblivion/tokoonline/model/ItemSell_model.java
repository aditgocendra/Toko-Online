package com.oblivion.tokoonline.model;

public class ItemSell_model {


    private String idUpload;
    private String headerAds;
    private String price;
    private String number;
    private String category;
    private String condition;
    private String city;
    private String address;
    private String urlPhotoItem;
    private String description;
    private String userId;
    private String userName;
    private String urlPhotoUser;
    private String tanggal;
    private String waktu;
    private String seen;
    private String favorite;


    public ItemSell_model(){

    }

    public ItemSell_model(String idUpload, String headerAds, String price, String number, String category, String condition, String city, String address, String urlPhotoItem, String description, String userId, String userName, String urlPhotoUser, String tanggal, String waktu, String seen, String favorite) {
        this.idUpload = idUpload;
        this.headerAds = headerAds;
        this.price = price;
        this.number = number;
        this.category = category;
        this.condition = condition;
        this.city = city;
        this.address = address;
        this.urlPhotoItem = urlPhotoItem;
        this.description = description;
        this.userId = userId;
        this.userName = userName;
        this.urlPhotoUser = urlPhotoUser;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.seen = seen;
        this.favorite = favorite;
    }

    public String getIdUpload() {
        return idUpload;
    }

    public void setIdUpload(String idUpload) {
        this.idUpload = idUpload;
    }

    public String getHeaderAds() {
        return headerAds;
    }

    public void setHeaderAds(String headerAds) {
        this.headerAds = headerAds;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrlPhotoItem() {
        return urlPhotoItem;
    }

    public void setUrlPhotoItem(String urlPhotoItem) {
        this.urlPhotoItem = urlPhotoItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNamaUser() {
        return userName;
    }

    public void setNamaUser(String namaUser) {
        this.userName = namaUser;
    }

    public String getUrlPhotoUser() {
        return urlPhotoUser;
    }

    public void setUrlPhotoUser(String urlPhotoUser) {
        this.urlPhotoUser = urlPhotoUser;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }
}
