package guipack;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.nio.file.StandardOpenOption.APPEND;

/**
 * Класс WorkingInformation предназначен для реализации логики для записи данных об отруженных шиннах в файл
 * в соответствующем формате
 */
public class WorkingInformation {
    public String operatorID = null;
    public int counterOfTyres = 0;
    public ArrayList<String> listOfBarcodes = null;
    private Path pathToFile = null;
    public boolean isFile = false;
    public boolean whithoutBarcode = false;

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }

    public String getOperatorID() {
        return operatorID;
    }

    public void writeToFile(String barcode) {
        String currentTime = getCurrentTime();

        try {
            //Если в текущей сессии файл не создан, необходимо создать его. Имя файла формируется из текущего времени
            if (!isFile) {
                pathToFile = createFile();
                isFile = true;
            }
            if (listOfBarcodes == null) {
                createListOfBarcodes();
            }
            OutputStream out = Files.newOutputStream(pathToFile, APPEND);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            if (whithoutBarcode) {
                writer.write(currentTime + ";;" + barcode + ";" + operatorID);
            } else {
                writer.write(currentTime + ";" + barcode + ";;" + operatorID);
            }
            writer.write("\r\n");
            writer.flush();
            writer.close();
            counterOfTyres++;
            listOfBarcodes.add(barcode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCounterOfTyres() {
        return counterOfTyres;
    }

    public String getLastBarcode() {
        return listOfBarcodes.get(counterOfTyres - 1);
    }

    public Path createFile() throws IOException {
        Path file;
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH-mm-ss");
        String currentTime = dateFormat.format(Calendar.getInstance().getTime());
        //Согласно ТЗ перед использыванием данной программы необходимо вручную, единожды создать директорию "С:\ScrapTires\"
        file = Files.createFile(Paths.get("C:/ScrapTires/", currentTime + ".txt"));
        return file;
    }

    public String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(Calendar.getInstance().getTime());
        return currentTime;
    }

    public void removePathToFile() {
        try {
            Files.delete(pathToFile);
            pathToFile = null;
            isFile = false;
            clearListOfBarcodes();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Path getPathToFile() {
        return pathToFile;
    }

    public void clearListOfBarcodes() {
        listOfBarcodes = null;
        counterOfTyres = 0;
    }

    public void createListOfBarcodes() {
        listOfBarcodes = new ArrayList<String>();
    }

}




