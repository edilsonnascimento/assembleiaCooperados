package br.org.enascimento.assembleiacooperados.common;

import java.time.LocalDateTime;

public class DataUtils {


    private DataUtils(){}

    public static boolean isMesmaData(LocalDateTime data1, LocalDateTime data2) {
        return data1.getYear() == data2.getYear() &&
                data1.getMonth() == data2.getMonth() &&
                data1.getDayOfMonth() == data2.getDayOfMonth() &&
                data1.getHour() == data2.getHour() &&
                data1.getMinute() == data2.getMinute();
    }
}
