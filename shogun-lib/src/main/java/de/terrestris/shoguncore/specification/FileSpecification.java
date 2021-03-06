package de.terrestris.shoguncore.specification;

import de.terrestris.shoguncore.model.File;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class FileSpecification {

    public static Specification<File> findByUuid(UUID fileUuid) {
        return (root, query, cb) -> {
            return cb.equal(root.get("fileUuid"), fileUuid);
        };
    }
}
