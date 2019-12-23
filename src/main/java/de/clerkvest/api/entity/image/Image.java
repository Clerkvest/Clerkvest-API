package de.clerkvest.api.entity.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "image")
@Entity
public class Image extends RepresentationModel {

    @Id
    @SequenceGenerator(name = "image_gen", sequenceName = "image_image_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "image_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "image_id", updatable = false)
    private Long imageId;

    @NotNull
    private String filename;

    @NotNull
    private String path;
}
