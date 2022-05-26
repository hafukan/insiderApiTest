package insider.model;


import lombok.*;


import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pet {
    private Integer id;
    private String name;
    private Category category;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;
}
