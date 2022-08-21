package front.services;

import front.commons.data_class.AttachmentData;
import front.commons.enums.APIs;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.UUID;

public class Cache {

    // files cache
    public static AttachmentData getAttachment(UUID attachmentId) {
        // todo
        return null;
    }

    public static void addAttachmentToCache(AttachmentData attachmentData) {
        // TODO
    }

    // requests cache
    static HashMap<APIs, HttpResponse<String>> singletonResponsesCache = new HashMap<>();

    public static void addSingletonResponseToCache(APIs api, HttpResponse<String> response) {
        singletonResponsesCache.put(api, response);
    }

    public static HttpResponse<String> getCachedSingletonResponse(APIs api) {
        return singletonResponsesCache.get(api);
    }


    static HashMap<UUID, HttpResponse<String>> chatFeedsDataCache = new HashMap<>();

    public static void addGetChatFeedDataResponse(UUID chatFeedId, HttpResponse<String> response) {
        chatFeedsDataCache.put(chatFeedId, response);
    }

    public static HttpResponse<String> getGetChatFeedDataResponse(UUID chatFeedId) {
        return chatFeedsDataCache.get(chatFeedId);
    }

}
