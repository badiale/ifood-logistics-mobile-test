# ifood-mobile-test
Descrição do problema pode ser encontrado [aqui](https://github.com/ifood/ifood-logistics-mobile-test)

## Arquitetura

Esse projeto está dividido em pacotes separados por funcionalidade. Por exemplo, o pacote `tweet` 
contém toda a lógica relacionada a manipular um tweet, analisá-lo e exibí-lo na tela, enquanto
o pacote `twitter` contém todos os serviços e objetos necessários para lidar com a comunicação
com o Twitter em si.

## Frameworks

Foram usadas as seguintes tecnologias/frameworks/patterns:

* Cardview e Recycler view: Ambos componentes visuais já bem consolidados como padrão em 
aplicativos android e bem eficientes.
* [ViewModel/LiveData](https://developer.android.com/topic/libraries/architecture/viewmodel.html):
Biblioteca que facilita a separação de código de negócio da lógica de apresentação, utilizando
o `LiveData` que é tipo um `Observable` e é notificado sempre que os dados do `ViewModel` forem
alterados, assim permitindo que a tela seja atualizada.
* [Picasso](http://square.github.io/picasso/): Biblioteca simples para buscar imagens da
internet e exibílas em `ImageView`s.
* [Butter Knife](http://jakewharton.github.io/butterknife/): Biblioteca para fazer bind de
views/eventos com campos de um objeto, evitando `findViewById` e deixando o código mais limpo.
* [Event Bus](https://github.com/greenrobot/EventBus): Implementação leve de um event bus para
android, que facilita a geração e consumo de eventos.
* [Retrofit](http://square.github.io/retrofit/): Biblioteca para simplificar chamadas à
seriços REST.
* [RxAndroid](https://github.com/ReactiveX/RxAndroid): Implementação de programação reativa
para android.
* [Guava](https://github.com/google/guava): Classes utilitárias de operações comuns.
* [Firebase](https://firebase.google.com/?hl=pt-br): O Firebase provê diversas bibliotecas.
As utilizadas são:
    * [JobDispatcher](https://github.com/firebase/firebase-jobdispatcher-android): Para criar
    tarefas de background que ficam rodando mesmo com o aplicativo fechado e é mais 
    compatível que a [JobScheduler](https://developer.android.com/reference/android/app/job/JobScheduler.html)
    nativa.
    * [Analytics](https://firebase.google.com/docs/analytics/?hl=pt-br): Para coletar dados
    sobre como os usuários usam o aplicativo.
    * [Crash Reports](https://firebase.google.com/docs/crash/?hl=pt-br): Para reportar crashs
    do aplicativo.
    * [Crashlytics](https://firebase.google.com/docs/crashlytics/?hl=pt-br): Versão melhorada
    de Crash Reports em junção com o [Fabric](https://get.fabric.io/), mas como ainda está
    em beta, o Crash Reports foi mantido.

### Menções honrosas

Injeção de dependências com o [Dagger](http://square.github.io/dagger/) ficou de fora do projeto
pelo escopo ser muito pequeno e as dependências, em sua maioria, se comportarem bem com o 
padrão singleton.

## Screenshots

![Tela principal](./docs/main_screen.png)
![Tweets buscados](./docs/tweet_list.png)
![Análise de sentimentos](./docs/tweet_list_sentiments.png)