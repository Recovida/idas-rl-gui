# IDaS-RL (GUI)

Esta é uma interface gráfica para o IDaS-RL, que é uma ferramenta de record
linkage desenvolvida por uma equipe do projeto
Recovida a partir do código
do [Cidacs-RL](https://github.com/gcgbarbosa/cidacs-rl-v1).

Uma versão de linha de comando está disponível no
[repositório IDaS-RL Core](https://gitlab.com/recovida/idas-rl-core).


## Execução

Inicialmente, [instale o Java](https://www.java.com/pt-BR/download/manual.jsp)
na versão 8 ou qualquer versão mais recente.

Faça o download da última versão do IDaS-RL (GUI) na página
[Releases](https://gitlab.com/recovida/idas-rl-gui/-/releases) deste
repositório.

Dependendo das configurações do seu sistema operacional, pode ser possível
abrir o programa clicando duas vezes no arquivo salvo, com extensão `.jar`,
ou clicando nele com o botão direito do mouse
e escolhendo a opção de abrir com o Java (JRE).

Para executar a partir de uma janela de linha de comando, abra um
terminal / prompt de comando / Powershell (a depender
do sistema operacional) e entre no diretório do arquivo `.jar`.
Para executar o programa, digite
```
java -jar idas-rl-gui-packaged.jar
```
(substituindo `idas-rl-gui-packaged.jar` pelo nome exato do arquivo salvo,
  que varia conforme a versão).

Caso queira abrir um arquivo de configurações automaticamente, passe o
nome desse arquivo como o único argumento do programa,
de acordo com o formato a seguir:
```
java -jar idas-rl-gui-packaged.jar arquivo-de-configurações.properties
```
(substituindo `idas-rl-gui-packaged.jar`
  e `arquivo-de-configurações.properties`
  pelos nomes exatos dos arquivos).


## Compilação

Este repositório utiliza o
[Maven](https://maven.apache.org/) para gerenciar o programa, o processo de
compilação e suas dependências.

Uma vez que este repositório tem o IDaS-RL (Core) como dependência,
sugere-se que **não** seja clonado diretamente. Em vez disso, clone
o [repositório-pai](https://gitlab.com/recovida/idas-rl), o que criará
os diretórios com ambos os repositórios, e então execute `git pull`
nos diretórios `idas-rl-core` e `idas-rl-gui`.

Estando na raiz do repositório-pai, execute ```mvn compile``` para compilar
o programa e ```mvn package```
para gerar o arquivo `.jar` no diretório `target` de cada repositório
(Core e GUI).

## Tradução

Para adicionar suporte a um novo idioma na interface para o usuário
(como menus, botões e mensagens),
acrescente ao arquivo
[languages.txt](src/main/resources/lang/languages.txt)
uma nova linha com a
[*language tag*](https://docs.oracle.com/javase/tutorial/i18n/locale/matching.html)
correspondente ao idioma, e crie um arquivo com extensão `.properties` no
diretório [`lang`](src/main/resources/lang/) com as mensagens traduzidas.
Utilize um dos idiomas existentes como base e mantenha o
[formato](https://docs.oracle.com/javase/8/docs/api/java/text/MessageFormat.html)
das mensagens com argumentos. Recomenda-se utilizar uma ferramenta como o
[ResourceBundle Editor](https://marketplace.eclipse.org/content/resourcebundle-editor)
para facilitar o processo.


## Licença

Os conteúdos deste repositório estão publicados sob a licença [MIT](LICENSE).
