package framework.apiserver.domain.board.book;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService{
    String url="http://nl.go.kr/NL/search/openApi";

    @Value("${open.api.key}")
    String key;
    private final RestTemplate restTemplate;

    @Override
    public List<BookDto> getBookList(int pageNum) {
        //사저 추천도서
        String request_url = url + "/saseoApi.do?key=" + key+"&pageNum="+pageNum;
        Response response = restTemplate.getForObject(request_url,Response.class);

        List<BookDto> list = new LinkedList<>();
        response.bookList.stream().forEach(book -> {
           list.add(new BookDto(
                   book.item.getRecomNo(),
                   book.item.getDrCode(),
                   book.item.getDrCodeName(),
                   book.item.getRecomtitle(),
                   book.item.getRecomauthor(),
                   book.item.getRecompublisher(),
                   book.item.getMokchFilePath()));
        });

        return list;
    }
}
