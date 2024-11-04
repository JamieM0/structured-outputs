# Structured Outputs
Structured Outputs are a way of interacting with a large language model while ensuring that its output conforms to a specified format.

This project is an implementation of the OpenAI API using a Java class.

## How to use
To get started, simply create a new Structure object, like this:

```java
Structure s = new Structure();
```

From here, you can easily add new properties of type:
* string
* number
* array

You can also assign descriptions for properties to explain to the LLM what it should output.

```java
addProperty(String name, String dataType, String description)

s.addProperty("animal", "string", "Give a specific animal");
s.addProperty("personality", "string", "Give your chosen animal a personality");
s.addProperty("joke", "string", "Have your chosen animal tell a joke in its personality");
s.addProperty("humour_level", "number", "How funny do you think this joke is? (Number from 0-1)");
```

When strict mode is on, the LLM will follow your structure exactly.
```java
s.setStrict(true);
```

### Arrays
Arrays are also supported in Structured Outputs. To set this up, first initialise your Structure. Then, use this syntax to setup your array.
```java
Structure.Items pets = s.addArrayProperty("pets", "List of pets");
pets.addProperty("species", "string", "Species of the pet");
pets.addProperty("name", "string", "Name of the pet");
pets.addProperty("age", "number", "Age of the pet");

//You can then set the required properties for the array
pets.addRequired("species");
pets.addRequired("name");
pets.addRequired("age");
```


##### Read the blog post [here](https://jmatthews.uk/structured-outputs).
