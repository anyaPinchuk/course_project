package message;

/**
 * Created by ANYA on 20.09.2016.
 */
public enum MessageType
{
    CONNECTION_REQUEST,

    USER_AUTHORIZATION,
    USER_REGISTRATION,
    USER_REGISTERED,
    USER_ACCEPTED,
    USER_NOT_FOUND,
    USER_ALREADY_EXIST,

    COMMON_USER,
    USER_ADMIN,

    SESSION_LIST,
    ADD_SESSION,
    SESSION_ADDED,
    FILM_LIST,

    BUY_ALL,
    SELECTED_SESSION,
    BOOK_ALL;
}
