/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ParserContext
 *  org.springframework.expression.common.TemplateParserContext
 *  org.springframework.expression.spel.SpelCompilerMode
 *  org.springframework.expression.spel.SpelParserConfiguration
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 */
package org.apereo.cas.util.spring;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.function.Function;
import lombok.Generated;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpringExpressionLanguageValueResolver
implements Function {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringExpressionLanguageValueResolver.class);
    private static final int HOUR_23 = 23;
    private static final int MINUTE_59 = 59;
    private static final int SECOND_59 = 59;
    private static final ParserContext PARSER_CONTEXT = new TemplateParserContext("${", "}");
    private static final SpelExpressionParser EXPRESSION_PARSER = new SpelExpressionParser(new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, SpringExpressionLanguageValueResolver.class.getClassLoader()));
    private static SpringExpressionLanguageValueResolver INSTANCE;
    private final StandardEvaluationContext evaluationContext = new StandardEvaluationContext();

    protected SpringExpressionLanguageValueResolver() {
        Properties properties = System.getProperties();
        this.evaluationContext.setVariable("systemProperties", (Object)properties);
        this.evaluationContext.setVariable("sysProps", (Object)properties);
        Map<String, String> environment = System.getenv();
        this.evaluationContext.setVariable("environmentVars", environment);
        this.evaluationContext.setVariable("environmentVariables", environment);
        this.evaluationContext.setVariable("envVars", environment);
        this.evaluationContext.setVariable("env", environment);
        this.evaluationContext.setVariable("tempDir", (Object)FileUtils.getTempDirectoryPath());
        this.evaluationContext.setVariable("zoneId", (Object)ZoneId.systemDefault().getId());
    }

    private void initializeDynamicVariables() {
        this.evaluationContext.setVariable("randomNumber2", (Object)RandomUtils.randomNumeric(2));
        this.evaluationContext.setVariable("randomNumber4", (Object)RandomUtils.randomNumeric(4));
        this.evaluationContext.setVariable("randomNumber6", (Object)RandomUtils.randomNumeric(6));
        this.evaluationContext.setVariable("randomNumber8", (Object)RandomUtils.randomNumeric(8));
        this.evaluationContext.setVariable("randomString4", (Object)RandomUtils.randomAlphabetic(4));
        this.evaluationContext.setVariable("randomString6", (Object)RandomUtils.randomAlphabetic(6));
        this.evaluationContext.setVariable("randomString8", (Object)RandomUtils.randomAlphabetic(8));
        this.evaluationContext.setVariable("uuid", (Object)UUID.randomUUID().toString());
        this.evaluationContext.setVariable("localDateTime", (Object)LocalDateTime.now(ZoneId.systemDefault()).toString());
        this.evaluationContext.setVariable("localDateTimeUtc", (Object)LocalDateTime.now(Clock.systemUTC()).toString());
        LocalDateTime localStartWorkDay = LocalDate.now(ZoneId.systemDefault()).atStartOfDay().plusHours(8L);
        this.evaluationContext.setVariable("localStartWorkDay", (Object)localStartWorkDay.toString());
        this.evaluationContext.setVariable("localEndWorkDay", (Object)localStartWorkDay.plusHours(9L).toString());
        LocalDateTime localStartDay = LocalDate.now(ZoneId.systemDefault()).atStartOfDay();
        this.evaluationContext.setVariable("localStartDay", (Object)localStartDay.toString());
        this.evaluationContext.setVariable("localEndDay", (Object)localStartDay.plusHours(23L).plusMinutes(59L).plusSeconds(59L).toString());
        this.evaluationContext.setVariable("localDate", (Object)LocalDate.now(ZoneId.systemDefault()).toString());
        this.evaluationContext.setVariable("localDateUtc", (Object)LocalDate.now(Clock.systemUTC()).toString());
        this.evaluationContext.setVariable("zonedDateTime", (Object)ZonedDateTime.now(ZoneId.systemDefault()).toString());
        this.evaluationContext.setVariable("zonedDateTimeUtc", (Object)ZonedDateTime.now(Clock.systemUTC()).toString());
    }

    public static SpringExpressionLanguageValueResolver getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SpringExpressionLanguageValueResolver();
        }
        INSTANCE.initializeDynamicVariables();
        return INSTANCE;
    }

    public String resolve(String value) {
        if (StringUtils.isNotBlank((CharSequence)value)) {
            LOGGER.trace("Parsing expression as [{}]", (Object)value);
            Expression expression = EXPRESSION_PARSER.parseExpression(value, PARSER_CONTEXT);
            String result = (String)expression.getValue((EvaluationContext)this.evaluationContext, String.class);
            LOGGER.trace("Parsed expression result is [{}]", (Object)result);
            return result;
        }
        return value;
    }

    public Object apply(Object o) {
        return this.resolve(o.toString());
    }
}

