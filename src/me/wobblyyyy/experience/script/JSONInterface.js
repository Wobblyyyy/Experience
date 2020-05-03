function getJSONObject ()
{
    return JSON.stringify(JSONObject);
}

function setJSONObject (input)
{
    JSONObject = JSON.parse(input);
}

function readJSON (path)
{
    if (!(path.substring(0, 2) === "[\""))
    {
        path = path.split("[").join("[\"").split("]").join("\"]");
    }
    try
    {
        return eval("JSONObject" + path) || 0;
    }
    catch (e)
    {
        return 0;
    }
}

function writeJSON (path, value)
{
    if (!(path.substring(0, 2) === "[\""))
    {
        path = path.split("[").join("[\"").split("]").join("\"]");
    }
    try
    {
        eval("JSONObject" + path + "=" + value);
        return "JSONObject" + path + "=" + value;
    }
    catch (e)
    {
        print("Error with writeJSON(): " + e);
    }
}

function incrementJSON (path, incrementBy)
{
    if (!(path.substring(0, 2) === "[\""))
    {
        path = path.split("[").join("[\"").split("]").join("\"]");
    }
    writeJSON(path, parseInt(readJSON(path)) + incrementBy);
}