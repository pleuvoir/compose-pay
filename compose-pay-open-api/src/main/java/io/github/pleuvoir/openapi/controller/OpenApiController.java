package io.github.pleuvoir.openapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.pleuvoir.openapi.model.dto.CreateActivityDTO;
import io.github.pleuvoir.openapi.model.dto.CreateActivityResultDTO;
import io.github.pleuvoir.openapi.model.dto.FightRedpackDTO;
import io.github.pleuvoir.openapi.model.dto.ResultMessageDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * OPEN API
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@RestController
@RequestMapping("/open-api")
public class OpenApiController {

    /**
     * 发红包
     */
    @RequestMapping(value = "createActivity", method = RequestMethod.POST)
    public ResultMessageDTO<CreateActivityResultDTO> createActivity(@RequestHeader("version") String version, @RequestBody CreateActivityDTO createActivityDTO) {
        return ResultMessageDTO.success(null);
    }


    /**
     * 抢红包
     */
    @RequestMapping(value = "fight", method = RequestMethod.POST)
    public ResultMessageDTO<Boolean> fight(@RequestHeader("version") String version, @RequestBody FightRedpackDTO fightRedpackDTO) {
        return ResultMessageDTO.success(null);
    }

}
