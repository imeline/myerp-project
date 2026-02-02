package erp.global.util;

public record PageParam(int page, int size) {
    public int offset() {
        return page * size;
    }

    public static PageParam of(Integer page, Integer size, int defaultSize) {
        int s = (size == null || size < 1) ? defaultSize : size;
        int p = (page == null || page < 0) ? 0 : page;
        return new PageParam(p, s);
    }
}
