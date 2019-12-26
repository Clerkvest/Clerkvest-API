package de.clerkvest.api.common.hateoas.constants;

/**
 * api <p>
 * de.clerkvest.api.common.hateoas.constants <p>
 * HateoasLink.java <p>
 *
 *     Class to hold all constants related to HATEOAS.
 *     All strings already got the field {@code BASE_ENDPOINT} concatenated.
 *
 * @author Danny B.
 * @version 1.0
 * @since 22 Dec 2019 11:33
 *
 * @see HateoasLink#BASE_ENDPOINT
 */
public class HateoasLink {

    /** API Base address without a "/" at the end */
    public static final String BASE_ENDPOINT = "http://localhost:8080";

    /** All companies */
    public static final String COMPANY_ALL = BASE_ENDPOINT + "/company/all";
    /**
     * Single company
     */
    public static final String COMPANY_SINGLE = BASE_ENDPOINT + "/company/get/";
    /**
     * Delete company
     */
    public static final String COMPANY_DELETE = BASE_ENDPOINT + "/company/delete/";
    /**
     * Create company
     */
    public static final String COMPANY_CREATE = BASE_ENDPOINT + "/company/create";
    /**
     * Update company
     */
    public static final String COMPANY_UPDATE = BASE_ENDPOINT + "/company/update";


    /**
     * All companies
     */
    public static final String EMPLOYEE_ALL = BASE_ENDPOINT + "/employee/all";
    /**
     * Single company
     */
    public static final String EMPLOYEE_SINGLE = BASE_ENDPOINT + "/employee/get/";
    /**
     * Delete company
     */
    public static final String EMPLOYEE_DELETE = BASE_ENDPOINT + "/employee/delete/";
    /**
     * Create company
     */
    public static final String EMPLOYEE_CREATE = BASE_ENDPOINT + "/employee/create";
    /**
     * Update company
     */
    public static final String EMPLOYEE_UPDATE = BASE_ENDPOINT + "/employee/update";

}
