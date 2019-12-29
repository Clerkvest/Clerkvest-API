package de.clerkvest.api.entity.image;

import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * api <p>
 * de.clerkvest.api.entity.image <p>
 * Image.java <p>
 *
 * @author Michael K.
 * @version 1.0
 * @since 21 Dec 2019 19:11
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "image")
@Entity
public class Image extends RepresentationModel<Image> implements IServiceEntity {

    @Id
    @SequenceGenerator(name = "image_gen", sequenceName = "image_image_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "image_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "image_id", updatable = false)
    private Long imageId;

    @NotNull
    private String filename;

    @NotNull
    private String path;

    @Override
    public Long getId() {
        return getImageId();
    }

    @Override
    public void setId(Long id) {
        setImageId(id);
    }

    @Override
    public String toString() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
