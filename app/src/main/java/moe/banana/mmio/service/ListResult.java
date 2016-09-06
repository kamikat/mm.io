package moe.banana.mmio.service;

import java.util.List;

@SuppressWarnings("unused")
public final class ListResult<ITEM> {
    public boolean error;
    public List<ITEM> results;
}
