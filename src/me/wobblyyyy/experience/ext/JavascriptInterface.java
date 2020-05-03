package me.wobblyyyy.experience.ext;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.InputStreamReader;

public class JavascriptInterface
{
    public String path;
    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    Invocable invocable = (Invocable) engine;

    public void setPath (String path)
    {
        this.path = path;
    }

    public void loadScripts (String... scripts)
    {
        try
        {
            for (String script : scripts)
            {
                engine.eval(new InputStreamReader(getClass().getResourceAsStream("/me/wobblyyyy/experience/script/" + script)));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String executeScriptFunction (String function, Object... arguments)
    {
        try
        {
            return invocable.invokeFunction(function, arguments).toString();
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            return "";
        }
    }
}