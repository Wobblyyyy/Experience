function newName (format, counterFormat, name, counter)
{
    return name.replace(counterFormat.replace("%counter%", counter.toString()), "") + counterFormat.replace("%counter%", (counter + 1).toString());
}