package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端-菜品浏览接口")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    @CacheEvict(cacheNames = "dishCache", key = "#categoryId")
    public Result<List<DishVO>> list(Long categoryId) {
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);//查询起售中的菜品

        List<DishVO> list = dishService.listWithFlavor(dish);

        return Result.success(list);
    }

/*    @Override
    public List<DishVO> listVO(Dish dish) {
//        不光要获得菜品的基本信息，而且要获得口味信息
//        1. 获得基本信息
        List<Dish> dishlist = dishMapper.list(dish);
//         2.根据基本信息获得口味信息
//        List<Dish>--根据dishid 查询口味-->List<DishVO>
//        使用stream的map函数
        List<DishVO> dishVOList = dishlist.stream().map(dish1 -> {
            DishVO dishVO = new DishVO();
//                todo给dishvo赋值
            BeanUtils.copyProperties(dish1, dishVO);

//           赋值口味

            List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(dish1.getId());
            dishVO.setFlavors(dishFlavors);
            return dishVO;
        }).collect(Collectors.toList());
        return dishVOList;
    }*/
}