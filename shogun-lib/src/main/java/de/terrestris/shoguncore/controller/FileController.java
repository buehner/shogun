package de.terrestris.shoguncore.controller;

import de.terrestris.shoguncore.model.File;
import de.terrestris.shoguncore.service.FileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/files")
public class FileController {

    protected final Logger LOG = LogManager.getLogger(getClass());

    @Autowired
    private FileService service;

    @Autowired
    protected MessageSource messageSource;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public File add(@RequestParam("file") MultipartFile uploadedFile) {

        LOG.debug("Requested to upload a multipart-file");

        try {
            File file = this.service.uploadFile(uploadedFile);

            LOG.info("Successfully uploaded file " + file.getFileName());

            return file;
        } catch (Exception e) {
            LOG.error("Could not upload the file: " + e.getMessage());
            LOG.trace("Full stack trace: ", e);

            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageSource.getMessage(
                    "BaseController.INTERNAL_SERVER_ERROR",
                    null,
                    LocaleContextHolder.getLocale()
                )
            );
        }
    }

    @GetMapping("/{fileUuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findOne(@PathVariable("fileUuid") UUID fileUuid) {

        LOG.debug("Requested to return file with UUID {}", fileUuid);

        try {
            final HttpHeaders responseHeaders = new HttpHeaders();

            File file = this.service.getFile(fileUuid);

            LOG.info("Successfully got file with UUID {}", fileUuid);

            responseHeaders.setContentType(MediaType.parseMediaType(file.getFileType()));
            responseHeaders.setContentDisposition(ContentDisposition.parse(
                String.format("inline; filename=\"%s\"", file.getFileName())));

            return new ResponseEntity<>(file.getFile(), responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error while returning the file {}: ",  e.getMessage());
            LOG.trace("Full stack trace: ", e);

            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageSource.getMessage(
                    "BaseController.INTERNAL_SERVER_ERROR",
                    null,
                    LocaleContextHolder.getLocale()
                )
            );
        }
    }
}
