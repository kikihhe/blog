package com.xiaohe.job;

import com.xiaohe.constants.Constants;
import com.xiaohe.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author : 每隔5分钟将redis的数据同步到mysql
 * @Description :
 * @date : 2023-01-09 15:34
 */
@Slf4j
@Component
@Async("threadPoolExecutor")
public class UpdateViewCountJob {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Scheduled(cron = "* 0/10 * * * ?")
    public void updateViewCountFromRedisToMysql() {
        log.info("定时任务执行了");

        Map<Object, Object> map =  stringRedisTemplate.opsForHash().entries(Constants.Article.BLOG_Article_VIEWCOUNT);



        map.forEach((key, value) -> {
            Long id = Long.valueOf((String) key);
            Long viewCount =Long.valueOf((String) value);
            threadPoolExecutor.execute(() -> {
                articleService.updateViewCount(id, viewCount);
            });
        });



    }
}
