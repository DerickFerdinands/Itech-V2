package Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DirectorDTO {
    private String id;
    private String name;
    private String nic;
    private String mobile;
    private String address;
    private double profitMargin;
}
