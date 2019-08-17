package com.products.demo.audit;

import com.products.demo.Product;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
class CsvWriter {

    private static final String SEPERATOR = ";";

    File createCsvFile(String filename, List<? extends Product> products) {
        Path csvOutputFile = Paths.get(filename);

        createDirectories(csvOutputFile.getParent());

        try (PrintWriter pw = new PrintWriter(csvOutputFile.toFile())) {

            for (Product row : products) {
                String line = toCsvLine(row);
                pw.println(line);
            }
            pw.flush();

        } catch (IOException e) {
            throw new ProductServiceAuditException("Error creating csv file " + filename, e);
        }

        return csvOutputFile.toFile();
    }

     String toCsvLine(Product product) {
        return product.getName() + SEPERATOR + product.getStockLevel();
    }

    private static void createDirectories(Path directoryToCreate) {
        try {
            if (!directoryToCreate.toFile().exists()) {
                Files.createDirectories(directoryToCreate);
            }
        } catch (IOException e) {
            throw new ProductServiceAuditException("Error creating directory " + directoryToCreate.normalize().toString(), e);
        }
    }
}
