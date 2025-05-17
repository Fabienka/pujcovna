package cz.bojdova.util;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private AtomicInteger nextBookId;
    private AtomicInteger nextUserId;

    public IdGenerator(List<Integer> existingBookIds, List<Integer> existingUserIds) {
        int maxBookId = existingBookIds.stream().mapToInt(Integer::intValue).max().orElse(0);
        int maxUserId = existingUserIds.stream().mapToInt(Integer::intValue).max().orElse(0);
        this.nextBookId = new AtomicInteger(maxBookId + 1);
        this.nextUserId = new AtomicInteger(maxUserId + 1);
    }

    public int getNextBookId() {
        return nextBookId.getAndIncrement();
    }

    public int getNextUserId() {
        return nextUserId.getAndIncrement();
    }
}
