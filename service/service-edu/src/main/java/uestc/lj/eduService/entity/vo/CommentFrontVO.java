package uestc.lj.eduService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentFrontVO {
    private String courseId;

    @ApiModelProperty(value = "最新时间排序")
    private Long gmtCreateSort;
}
