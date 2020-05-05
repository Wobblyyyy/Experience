function newName (format, counterFormat, name, counter)
{
    var newName = format;
    if (!counter > 0)
    {
        newName = newName.replace("%name%", name);
        newName = newName.replace("%counter%", counterFormat.replace("%counter%", "1"));
    }
    else
    {
        var oldCounter = counterFormat.replace("%counter%", counter.toString());
        var newCounter = counterFormat.replace("%counter%", (counter + 1).toString());
        newName = name.replace(oldCounter, newCounter);
    }
    return newName;
}