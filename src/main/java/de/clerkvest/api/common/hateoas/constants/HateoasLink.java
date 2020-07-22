package de.clerkvest.api.common.hateoas.constants;

/**
 * api <p>
 * de.clerkvest.api.common.hateoas.constants <p>
 * HateoasLink.java <p>
 * <p>
 * Class to hold all constants related to HATEOAS.
 * All strings already got the field {@code BASE_ENDPOINT} concatenated.
 *
 * @author Danny B.
 * @version 1.0
 * @see HateoasLink#BASE_ENDPOINT
 * @since 22 Dec 2019 11:33
 */
public class HateoasLink {

    /**
     * API Base address without a "/" at the end
     */
    public static final String BASE_ENDPOINT = "http://localhost:8080/api";
    /**
     * Single company
     */
    public static final String COMPANY_SINGLE = BASE_ENDPOINT + "/company/get/";
    /**
     * Create company
     */
    public static final String COMPANY_CREATE = BASE_ENDPOINT + "/company/create";
    /**
     * Update company
     */
    public static final String COMPANY_UPDATE = BASE_ENDPOINT + "/company/update";


    /**
     * All Employees
     */
    public static final String EMPLOYEE_ALL = BASE_ENDPOINT + "/employee/all";
    /**
     * Single Employee
     */
    public static final String EMPLOYEE_SINGLE = BASE_ENDPOINT + "/employee/get/";
    /**
     * Delete Employee
     */
    public static final String EMPLOYEE_DELETE = BASE_ENDPOINT + "/employee/delete/";
    /**
     * Create Employee
     */
    public static final String EMPLOYEE_CREATE = BASE_ENDPOINT + "/employee/create";
    /**
     * Update Employee
     */
    public static final String EMPLOYEE_UPDATE = BASE_ENDPOINT + "/employee/update";


    /**
     * All Investments
     */
    public static final String INVEST_ALL = BASE_ENDPOINT + "/invest/all";
    /**
     * Single Investment
     */
    public static final String INVEST_SINGLE = BASE_ENDPOINT + "/invest/get/";
    /**
     * Delete Investment
     */
    public static final String INVEST_DELETE = BASE_ENDPOINT + "/invest/delete/";
    /**
     * Create Investment
     */
    public static final String INVEST_CREATE = BASE_ENDPOINT + "/invest/create";
    /**
     * Update Investment
     */
    public static final String INVEST_UPDATE = BASE_ENDPOINT + "/invest/update";

    /**
     * All Projects
     */
    public static final String PROJECT_ALL = BASE_ENDPOINT + "/project/all";
    /**
     * Single Project
     */
    public static final String PROJECT_SINGLE = BASE_ENDPOINT + "/project/get/";
    /**
     * Delete Project
     */
    public static final String PROJECT_DELETE = BASE_ENDPOINT + "/project/delete/";
    /**
     * Create Project
     */
    public static final String PROJECT_CREATE = BASE_ENDPOINT + "/project/create";
    /**
     * Update Project
     */
    public static final String PROJECT_UPDATE = BASE_ENDPOINT + "/project/update";


    /**
     * Delete Comment
     */
    public static final String PROJECT_COMMENT_DELETE = BASE_ENDPOINT + "/comment/delete/";
    /**
     * Create Comment
     */
    public static final String PROJECT_COMMENT_CREATE = BASE_ENDPOINT + "/comment/create";
    /**
     * Update Comment
     */
    public static final String PROJECT_COMMENT_UPDATE = BASE_ENDPOINT + "/comment/update";

    /**
     * Employee Setting Self
     */
    public static final String EMPLOYEE_SETTING_SINGLE = BASE_ENDPOINT + "/settings/get/";
}
