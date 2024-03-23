package com.aex.platform.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log
@Service
public class FileStorageService {
    // Directorio donde se guardarán los archivos
    private static final String UPLOAD_DIR = "/downloads";

    public Path save(String folder, MultipartFile archivo, String dni, Long voucherId) throws IOException {
        // Verifica si el archivo no está vacío
        log.info("Guardando archivo");
        if (!archivo.isEmpty()) {
            log.info("Obtienendo los bytes del archivo");
            // Obtiene los bytes del archivo
            byte[] bytes = archivo.getBytes();

            // Crea la carpeta personalizada si aún no existe
            File directorio = new File(UPLOAD_DIR + "/" + folder + "/" + dni);
            if (!directorio.exists()) {
                log.info("Creando directorio" + directorio.toString());
                if (directorio.mkdirs()) {
                    log.info("Directorio creado " + directorio.toString());
                } else {
                    log.info("No se pudo crear el directorio " + directorio.toString());
                }
            }
            Long prefijo = voucherId;
            // Obtiene el nombre original del archivo
            String nombreArchivo = archivo.getOriginalFilename().replace(" ", "_");
            ;

            log.info("NombreArchivo " + nombreArchivo);

            // Crea la ruta completa del archivo
            Path ruta = Paths.get(UPLOAD_DIR + File.separator + folder + File.separator + dni + File.separator + prefijo + "_" + nombreArchivo);
            // Guarda los bytes en el archivo en la carpeta personalizada

            String rutaCompleta = UPLOAD_DIR + File.separator + folder + File.separator + dni + File.separator + prefijo + "_" + nombreArchivo;
            log.info("Guardando archivo");
            // Guarda el archivo en la carpeta personalizada
            archivo.transferTo(new File(rutaCompleta));

            File file = new File(rutaCompleta);
            if (file.exists()) {
                log.info("el archivo fue creado");
            } else {
                log.info("el archivo  NO fue creado");
            }
            return Files.write(ruta, bytes);
        }
        return null;
    }
}
