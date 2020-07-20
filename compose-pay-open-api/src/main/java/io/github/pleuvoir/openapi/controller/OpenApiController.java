package io.github.pleuvoir.openapi.controller;

import io.github.pleuvoir.openapi.model.dto.CreateActivityDTO;
import io.github.pleuvoir.openapi.model.dto.CreateActivityResultDTO;
import io.github.pleuvoir.openapi.model.dto.FightRedpackDTO;
import io.github.pleuvoir.openapi.model.dto.ResultMessageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * OPEN API
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@RestController
@RequestMapping("/open-api")
@Api(value = "OpenApiController", description = "开放API")
public class OpenApiController {

    /**
     * 发红包
     */
    @ApiOperation(value = "发红包", notes = "创建红包活动", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "createActivity", method = RequestMethod.GET)
    public ResultMessageDTO<CreateActivityResultDTO> createActivity(@ApiParam @RequestBody CreateActivityDTO createActivityDTO) {
        return new ResultMessageDTO();
    }


    /**
     * 抢红包
     */
    @RequestMapping(value = "fight", method = RequestMethod.GET)
    public ResultMessageDTO<Boolean> fight(@RequestBody FightRedpackDTO fightRedpackDTO) {
        return new ResultMessageDTO();
    }

}
