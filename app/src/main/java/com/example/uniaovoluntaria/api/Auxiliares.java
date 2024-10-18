package com.example.uniaovoluntaria.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Patterns;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

public class Auxiliares {


    public static void preferencesManagerIncluir(Context context, String keyValorTotal, String valueValorTotal) {
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyValorTotal, valueValorTotal);
        editor.commit();
    }

    public static String preferencesManagerObter(Context context, String key, String defaultValue) {
        return context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString(key, defaultValue);
    }


    public static String formatarNumero(String valorFornecido) {
        Double valor = Double.parseDouble(valorFornecido);
        DecimalFormat df = new DecimalFormat("R$ ###,###,##0.00");
        return df.format(valor);
    }

    public static boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static void alert(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public static void alertCustom(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    public static Boolean isBranco(EditText edt) {

        Boolean vazio = false;

        if (edt.getText().toString().isEmpty()) {
            edt.setError("Obrigatório*");
            edt.requestFocus();
            vazio = true;
        }
        return vazio;
    }

    public static String getDataAtual() {
        String dia, mes, ano;
        String dataAtual = "00/00/0000";
        try {
            Calendar calendar = Calendar.getInstance();

            dia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            mes = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            ano = String.valueOf(calendar.get(Calendar.YEAR));

            dia = (Calendar.DAY_OF_MONTH < 10) ? "0" + dia : dia;
            dia = (dia.length() > 2) ? dia.substring(1, 3) : dia;
            int mesAtual = (Calendar.MONTH) + 1;
            mes = (mesAtual <= 9) ? "0" + mes : mes;
            mes = (mes.length() > 2) ? mes.substring(1, 3) : mes;
            dataAtual = dia + "/" + mes + "/" + ano;
            return dataAtual;
        } catch (Exception e) {
        }
        return dataAtual;
    }

    public static String getDataHoraAtual() {
        // data/hora atual
        LocalDateTime agora = LocalDateTime.now();

        // formatar a data
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        String dataFormatada = formatterData.format(agora);

        // formatar a hora
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormatada = formatterHora.format(agora);

        return (dataFormatada + "-" + horaFormatada);
    }

    public static String getHoraAtual() {
        String horaAtual = "00:00:00";
        String hora, minuto, segundo;
        try {
            Calendar calendar = Calendar.getInstance();

            int iHora = calendar.get(Calendar.HOUR_OF_DAY);
            int iMinuto = calendar.get(Calendar.MINUTE);
            int iSegundo = calendar.get(Calendar.SECOND);

            hora = (iHora <= 9) ? "0" + iHora : Integer.toString(iHora);
            minuto = (iMinuto <= 9) ? "0" + iMinuto : Integer.toString(iMinuto);
            segundo = (iSegundo <= 9) ? "0" + iSegundo : Integer.toString(iSegundo);

            horaAtual = hora + ":" + minuto + ":" + segundo;

            return horaAtual;
        } catch (Exception e) {
        }
        return horaAtual;
    }

    public static boolean isCNPJ(String CNPJ) {
        // considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
                CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
                CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
                CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
                CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
                (CNPJ.length() != 14))
            return (false);

        char dig13, dig14;
        int sm, i, r, num, peso;

        try {

            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char) ((11 - r) + 48);


            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char) ((11 - r) + 48);

            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
                return (true);
            else return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static boolean isCPF(String CPF) {
        CPF = CPF.replace(".", "").replace("-", "");
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return (false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {

            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {

                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char) (r + 48);


            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char) (r + 48);

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return (true);
            else return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static String mascaraCNPJ(String CNPJ) {

        String retorno;

        retorno = (CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "." +
                CNPJ.substring(5, 8) + "." + CNPJ.substring(8, 12) + "-" +
                CNPJ.substring(12, 14));

        return retorno;
    }

    public static String mascaraCPF(String CPF) {//9999999999 ==> 999.999.999-9
        return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
                CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }

    public static String retiraMascaraCPF(String CPF) {//999.999.999-9 ==> 9999999999
        return (CPF.replace(".", "").replace("-", ""));
    }
}
