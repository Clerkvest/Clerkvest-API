package de.clerkvest.api.common.hateoas.link;

import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.Data;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

/**
 * api <p>
 * de.clerkvest.api.common.hateoas.link <p>
 * LinkBuilder.java <p>
 * <p>
 * Build class to add HATEOAS field {@code _link} to a object that extends {@code RepresentationModel}. <p>
 * <p>
 * Example:
 * <pre>
 *     LinkBuilder<Company> linkBuilder = new LinkBuilder<Company>()
 *          .withSelf(HateoasLink.COMPANY_SINGLE)
 *          .withAll(HateoasLink.COMPANY_ALL)
 *          .withCreate(HateoasLink.COMPANY_CREATE)
 *          .withDelete(HateoasLink.COMPANY_DELETE)
 *          .withUpdate(HateoasLink.COMPANY_UPDATE);
 *      linkBuilder.ifDesiredEmbed(company);
 *      </pre>
 *
 * @author Danny B.
 * @version 1.0
 * @see RepresentationModel
 * @see IServiceEntity
 * @since 22 Dec 2019 12:28
 */
@Data
public class LinkBuilder<T extends RepresentationModel<T> & IServiceEntity> {

    /**
     * _link to itself
     */
    private String self = HateoasLink.BASE_ENDPOINT;

    /**
     * _link to all entities
     */
    private String all = HateoasLink.BASE_ENDPOINT;

    /**
     * _link to delete a entity
     */
    private String delete = HateoasLink.BASE_ENDPOINT;

    /**
     * _link to create a entity
     */
    private String create = HateoasLink.BASE_ENDPOINT;

    /**
     * _link to update a entity
     */
    private String update = HateoasLink.BASE_ENDPOINT;

    /**
     * Defines if self should be embedded into _link
     */
    private boolean withSelf = false;

    /**
     * Defines if all should be embedded into _link
     */
    private boolean withAll = false;

    /**
     * Defines if delete of an entity should be embedded into _link
     */
    private boolean withDelete = false;

    /**
     * Defines if create of an entity should be embedded into _link
     */
    private boolean withCreate = false;

    /**
     * Defines if update of an entity should be embedded into _link
     */
    private boolean withUpdate = false;

    /**
     * Creates an instance of LinkBuilder with default values;
     */
    public LinkBuilder() {
    }

    /**
     * Sets the property withSelf to true.
     * And sets the link to itself.
     *
     * @param link link to self
     * @see LinkBuilder#withSelf
     */
    public LinkBuilder<T> withSelf(String link) {
        this.withSelf = true;
        this.self = link;
        return this;
    }

    /**
     * Sets the property withAll to true.
     * And sets the link to all entities.
     *
     * @param link link to all
     * @see LinkBuilder#withAll
     */
    public LinkBuilder<T> withAll(String link) {
        this.withAll = true;
        this.all = link;
        return this;
    }

    /**
     * Sets the property withDelete to true.
     * And sets the link to delete an entity.
     *
     * @param link link to delete
     * @see LinkBuilder#withDelete
     */
    public LinkBuilder<T> withDelete(String link) {
        this.withDelete = true;
        this.delete = link;
        return this;
    }

    /**
     * Sets the property withCreate to true.
     * And sets the link to create an entity.
     *
     * @param link link to create
     * @see LinkBuilder#withCreate
     */
    public LinkBuilder<T> withCreate(String link) {
        this.withCreate = true;
        this.create = link;
        return this;
    }

    /**
     * Sets the property withUpdate to true.
     * And sets the link to update an entity.
     *
     * @param link link to update
     * @see LinkBuilder#withUpdate
     */
    public LinkBuilder<T> withUpdate(String link) {
        this.withUpdate = true;
        this.update = link;
        return this;
    }

    /**
     * Embeds self link into _link if withSelf is true
     *
     * @param model Model to embed into
     * @see LinkBuilder#withSelf(String)
     * @see LinkBuilder#withSelf
     */
    private void embedSelf(T model) {
        if (withSelf) {
            model.add(new Link(
                    this.self + model.getId()).withSelfRel()
            );
        }
    }

    /**
     * Embeds all link into _link if withAll is {@code true}
     *
     * @param model Model to embed into
     * @see LinkBuilder#withAll(String)
     * @see LinkBuilder#withAll
     */
    private void embedAll(T model) {
        if (withAll) {
            model.add(new Link(this.all).withRel("all"));
        }
    }

    /**
     * Embeds delete link into _link if withDelete is {@code true}
     *
     * @param model Model to embed into
     * @see LinkBuilder#withDelete(String)
     * @see LinkBuilder#withDelete
     */
    private void embedDelete(T model) {
        if (withDelete) {
            model.add(new Link(
                    this.delete + model.getId()).withRel("delete")
            );
        }
    }

    /**
     * Embeds create link into _link if withCreate is {@code true}
     *
     * @param model Model to embed into
     * @see LinkBuilder#withCreate(String)
     * @see LinkBuilder#withCreate
     */
    private void embedCreate(T model) {
        if (withCreate) {
            model.add(new Link(this.create).withRel("create"));
        }
    }

    /**
     * Embeds update link into _link if withUpdate is {@code true}
     *
     * @param model Model to embed into
     * @see LinkBuilder#withUpdate(String)
     * @see LinkBuilder#withUpdate
     */
    private void embedUpdate(T model) {
        if (withUpdate) {
            model.add(new Link(this.update).withRel("update"));
        }
    }

    /**
     * Embeds all desired links into the object
     *
     * @param model model
     * @see LinkBuilder#embedSelf(T)
     * @see LinkBuilder#embedAll(T)
     * @see LinkBuilder#embedDelete(T)
     * @see LinkBuilder#embedCreate(T)
     * @see LinkBuilder#embedUpdate(T)
     */
    public void ifDesiredEmbed(T model) {
        embedSelf(model);
        embedAll(model);
        embedDelete(model);
        embedCreate(model);
        embedUpdate(model);
    }
}
