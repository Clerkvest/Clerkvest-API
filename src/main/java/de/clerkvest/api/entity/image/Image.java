package de.clerkvest.api.entity.image;

import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
}
