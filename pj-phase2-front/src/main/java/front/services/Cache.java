package front.services;

import front.commons.data_class.AttachmentData;
import front.commons.enums.APIs;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.UUID;

public class Cache {

    static HashMap<UUID, AttachmentData> attachments = new HashMap<>();
    // requests cache
    static HashMap<APIs, HttpResponse<String>> singletonResponsesCache = new HashMap<>();
    static HashMap<UUID, HttpResponse<String>> chatFeedsDataCache = new HashMap<>();

    // files cache
    public static AttachmentData getAttachment(UUID attachmentId) {
        return attachments.get(attachmentId);
    }

    public static void addAttachmentToCache(AttachmentData attachmentData) {
        attachments.put(attachmentData.id, attachmentData);
    }

    public static void addSingletonResponseToCache(APIs api, HttpResponse<String> response) {
        singletonResponsesCache.put(api, response);
    }

    public static HttpResponse<String> getCachedSingletonResponse(APIs api) {
        return singletonResponsesCache.get(api);
    }

    public static void addGetChatFeedDataResponse(UUID chatFeedId, HttpResponse<String> response) {
        chatFeedsDataCache.put(chatFeedId, response);
    }

    public static HttpResponse<String> getGetChatFeedDataResponse(UUID chatFeedId) {
        return chatFeedsDataCache.get(chatFeedId);
    }

}
