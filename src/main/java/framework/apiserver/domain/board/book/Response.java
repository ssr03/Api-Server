package framework.apiserver.domain.board.book;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@Setter
@XmlRootElement(name="channel")
public class Response{
    String totalCount;

    List<BookList> bookList;

    public String getTotalCount() {
        return totalCount;
    }

    @XmlElement(name="list")
    public List<BookList> getBookList() {
        return bookList;
    }

    @Getter
    @Setter
    @XmlRootElement(name="list")
    public static class BookList{
       Item item;
    }

    @Getter
    @Setter
    @XmlRootElement(name="item")
    public static class Item{
        String recomNo;
        String drCode;
        String drCodeName;
        String recomtitle;
        String recomauthor;
        String recompublisher;
        String mokchFilePath;
        String recomisbn;
    }
}
