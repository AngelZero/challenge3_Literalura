package com.angel.literalura.classes;

import com.angel.literalura.models.RSearch;

import java.util.List;

public class CSearch {

    private int count;
    private String next;
    private String previous;
    private List<CLibros> libros;

    public CSearch(RSearch search) {
        this.count = search.count();
        this.next = search.next();
        this.previous = search.previous();
        this.libros = search.libros().stream()
                .map(l -> new CLibros(l))
                .toList();
    }

    @Override
    public String toString() {
        return "CSearch{\n" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", libros=" + libros +
                '}';
    }

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<CLibros> getLibros() {
        return libros;
    }

}
