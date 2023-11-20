package jpabook.JpaShop.domain.item.controller;

import jpabook.JpaShop.domain.item.dto.BookForm;
import jpabook.JpaShop.domain.item.entity.Book;
import jpabook.JpaShop.domain.item.entity.Item;
import jpabook.JpaShop.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){
        Book book = Book.create(form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, Model model){
        Book item = (Book) itemService.findOne(itemId);

        BookForm bookForm = new BookForm();
        bookForm.setId(item.getId());
        bookForm.setName(item.getName());
        bookForm.setPrice(item.getPrice());
        bookForm.setStockQuantity(item.getStockQuantity());
        bookForm.setAuthor(bookForm.getIsbn());
        bookForm.setIsbn(item.getIsbn());

        model.addAttribute("form", bookForm);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form){

        // JPA가 식별할 수 있는 ID가 있는 값을 준영속 상태라고 한다.
        // JPA가 기볹적으로 관리하지 않음
//        Book book = Book.update(form.getId(), form.getName(), form.getPrice(),
//                form.getStockQuantity(), form.getAuthor(), form.getIsbn());

        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());
        return "redirect:/items";
    }

}
