package com.example.konsulyuk.Models;

public class StatementKuesioner {

    private String StatementAnswer [][] = {
            {"Petualang","Memberi Semangat","Mudah Menyesuaikan Diri","Teliti"},    //1
            {"Senang Membujuk","Suka Bercerita","Suka Kedamaian","Berpendirian Teguh"}, //2
            {"Berkemauan Kuat","Pandai Bergaul","Mau Mengalah","Suka Berkorban"},   //3
            {"Percaya Diri","Bersemangat","Peka / Perasa","Cepat Puas"},   //4
            {"Suka Berbicara Terus Terang","Optimis","Sopan / Hormat","Rapi"},   //5
            {"Mandiri","Idealis","Suka Memberi Inspirasi","Percaya Pada Ide Teman"},   //6
            {"Sukar Bertoleransi","Gampang Berubah Pendirian","Lalai / Acuh Tak Acuh","Sulit Membuka Diri"},   //7
            {"Berpikir Matematis","Lucu / Humoris","Mudah Menerima Saran","Suka Memimpin"},   //8
            {"Keras Kepala","Serampangan / Ceroboh","Bimbang / Ragu-Ragu","Sulit Mengiklaskan"},   //9
            {"Cepat Marah","Sulit Berkonsentrasi","Sering Ogah","Sering Berprasangka"},   //10
            {"","","",""}
    };

    public String getChoice(int index, int num){
        String statement = StatementAnswer[index][num-1];
        return statement;
    }
}
