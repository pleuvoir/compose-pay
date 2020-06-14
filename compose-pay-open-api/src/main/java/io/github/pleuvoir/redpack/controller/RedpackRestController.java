package io.github.pleuvoir.redpack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.pleuvoir.redpack.exception.RedpackException;
import io.github.pleuvoir.redpack.factory.VersionFactory;
import io.github.pleuvoir.redpack.model.dto.CreateActivityDTO;
import io.github.pleuvoir.redpack.model.dto.CreateActivityResultDTO;
import io.github.pleuvoir.redpack.model.dto.FightRedpackDTO;
import io.github.pleuvoir.redpack.model.dto.ResultMessageDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * 红包  API
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@RestController
@RequestMapping("/redpack")
public class RedpackRestController {


    @Autowired
    private VersionFactory versionFactory;

    /**
     * 发红包
     */
    @RequestMapping(value = "createActivity", method = RequestMethod.POST)
    public ResultMessageDTO<CreateActivityResultDTO> createActivity(@RequestHeader("version") String version, @RequestBody CreateActivityDTO createActivityDTO) {
        return ResultMessageDTO.success(versionFactory.route(version).create(createActivityDTO));
    }


    /**
     * 抢红包
     */
    @RequestMapping(value = "fight", method = RequestMethod.POST)
    public ResultMessageDTO<Boolean> fight(@RequestHeader("version") String version, @RequestBody FightRedpackDTO fightRedpackDTO) {
        try {
            return ResultMessageDTO.success(versionFactory.route(version).fight(fightRedpackDTO));
        } catch (RedpackException e) {
            log.warn("抢红包异常，redpackId={}，userId={}，{}", fightRedpackDTO.getActivityId(), fightRedpackDTO.getUserId(), e);
            return ResultMessageDTO.fail(e.getMessage());
        }
    }

}
