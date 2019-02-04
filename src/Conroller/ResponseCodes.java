package Conroller;

public class ResponseCodes
{
    public static final int OK=200;
    public static final int MOVED_PERMANENTLY=301;
    public static final int MOVED_TEMPORARILY=302;
    public static final int BAD_REQUEST=400;//синтаксическая ошибка в запросе сервера
    public static final int UNAUTHORIZED=401;//для доступа к запрашиваему ресурсу требуется аутентификация
    public static final int FORBIDDEN=403;//сервер понял запрос, но он отказывется его выполнять из-за ограниченности доступа пользователя к ресурсу
    public static final int NOT_FOUND=404;
    public static final int NOT_ACCEPTABLE=406;//запрошенный URI не может удовлетворить переданным в заголовке характеристикам. Если метод был не HEAD, то сервер должен вернуть список допустимых характеристик для данного ресурса
    public static final int REQUEST_TIMEOUT=408;//время ожидания сервером передачи от клиента истекло
    public static final int INTERNAL_SERVER_ERROR=500;//любая внутренняя ошибка сервера, которая не входит в рамки остальных ошибок класса
    public static final int NOT_IMPLEMENTED=501;//сервер не поддерживает возможностей, необходимых для обработки запроса
    public static final int SERVICE_UNAVAILABLE=503;//сервер временно не имеет возможности обрабатывать запросы по техническим причинам
    public static final int HTTP_VERSION_NOT_SUPPORTED=505;//сервер не поддерживает или отказывается поддерживать указанную в запросе версию протокола HTTP.

    public static String getInformationAbout(int code)
    {
        switch(code)
        {
            case 200:
                return "OK";
            case 400:
                return "Bad Request";
            case 501:
                return "Not Implemented";
        }
        return "Unknown Response Code";
    }
}