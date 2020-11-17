/**
 * A group of function representing the required functions in the software
 * requirement.
 * <p>
 * {@link Function} defines all the interface for all the function. Each
 * instance of the {@link Function} represents one function stated in the
 * software requirement in assignment manual.
 * <p>
 * The accessibility of {@link User} to {@link Function} is controlled by the
 * {@link Function} itself.
 * <p>
 * All the subclass of {@link Function} shall extend {@link AbstractFunction}
 * and be implemented with Singleton pattern.
 * <p>
 * All the functions shall be found in the iterable returned by
 * {@link AbstractFunction#allFunctions()}
 */
package cx2002grp2.stars.functions;

import cx2002grp2.stars.data.dataitem.User;