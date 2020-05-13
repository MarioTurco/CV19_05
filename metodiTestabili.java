Lista metodi che potremmo testare:
AggiungiRecensioneFragment.java

vedere metodo 13

1)
private boolean checkCampiNonVuoti(){
        if(titoloRecensione.getText().toString().equals("") ||
                testoRecensione.getText().toString().equals("") ||
                ratingBar.getRating() == 0)
            return false;
        else return true;
    }


2)
 private Recensione costruisciRecensioneDaInserire(){
        Recensione recensioneDaAggiungere = new Recensione();
        if(checkCampiNonVuoti()) {
            recensioneDaAggiungere.setStruttura(idStruttura);
            recensioneDaAggiungere.setStatoRecensione("In Attesa");
            recensioneDaAggiungere.setAutore(getNickname());
            recensioneDaAggiungere.setValutazione((int) ratingBar.getRating());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu");
            LocalDateTime now = LocalDateTime.now();
            recensioneDaAggiungere.setDataRecensione(dtf.format(now));
            recensioneDaAggiungere.setTesto(testoRecensione.getText().toString());
            recensioneDaAggiungere.setTitolo(titoloRecensione.getText().toString());
        }
        else throw new IllegalArgumentException("Compila tutti i campi!");
        return recensioneDaAggiungere;
}

3)private String getNickname() {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    return sharedPreferences.getString("nickname", "");
}


DeattagliStruttura
4) private boolean isUserLogged() {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    return sharedPreferences.getBoolean("isLogged", false);
}

FiltriFragment
5) private boolean hasFineLocationAccess(){
    return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED;
}
6) private boolean hasCoarseLocationAccess(){
    return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED;
}
7) private boolean hasGPSPermissions(){
    return hasFineLocationAccess() && hasCoarseLocationAccess();
}

SignupFragment
8)  private boolean checkCampiNonVuoti(){
        if(emailEditText.getText().toString().equals("") ||
                nicknameEditText.getText().toString().equals("") ||
                nomeEditText.getText().toString().equals("") ||
                cognomeEditText.getText().toString().equals("") ||
                PasswordEditText.getText().toString().equals("") ||
                dataDiNascita.getText().toString().equals(""))
            return false;
        else return true;
    }

9)  private boolean checkLunghezzaPassword(){
        if(PasswordEditText.getText().toString().length() < 5)
            return false;
        else return true;
    }

10)  private Utente creaUtenteDaInserire(){
        Utente utenteDaAggiungere = new Utente();
        if(checkCampiNonVuoti()) {
            if(checkLunghezzaPassword()) {
                utenteDaAggiungere.setEmail(emailEditText.getText().toString());
                utenteDaAggiungere.setMostraNickname(mostraNicknameCheckbox.isSelected());
                utenteDaAggiungere.setNickname(nicknameEditText.getText().toString());
                utenteDaAggiungere.setNome(nomeEditText.getText().toString() + " " + cognomeEditText.getText().toString());
                String salt = PasswordUtils.getSalt(30);
                String passwordCriptata = null;
                passwordCriptata = PasswordUtils.generateSecurePassword(PasswordEditText.getText().toString(), salt);
                utenteDaAggiungere.setPassword(passwordCriptata);
                utenteDaAggiungere.setSalt(salt);
                utenteDaAggiungere.setDataDiNascita(dataDiNascita.getText().toString());
            }
            else throw new IllegalArgumentException("La password deve essere di almeno 5 caratteri!");
        }
        else throw new IllegalArgumentException("Compila tutti i campi");
        return utenteDaAggiungere;
    }

MainActivity

11) public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.login:
                setYourPositionButtonInvisible();
                loadLoginFragment();
                break;
            case R.id.signup:
                setYourPositionButtonInvisible();
                loadSignupFragment();
                break;
            case R.id.homepage:
                Toast.makeText(MainActivity.this, "Homepage selezionato", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(MainActivity.this, "Logout effettuato", Toast.LENGTH_SHORT).show();
                logout();
                updateDrawer();
        }
        return false;
    }

UtenteDAO

12)  private boolean checkPassword(JSONObject jsonObject, String givenPassword) throws JSONException{
        String correctPassword=null,salt=null;
        correctPassword = jsonObject.getString("password");
        salt = jsonObject.getString("salt");
        System.out.println(givenPassword);
        return PasswordUtils.verifyUserPassword(givenPassword,correctPassword,salt);
    }

13) public String buildInsertString(Utente utente){
        String queryRequestString = "https://m6o9t2bfx0.execute-api.eu-central-1.amazonaws.com/insert/utente?";
        queryRequestString += "nome=" + utente.getNome();
        queryRequestString += "&nickname=" + utente.getNickname();
        queryRequestString += "&email=" + utente.getEmail();
        queryRequestString += "&data_di_nascita=" + utente.getDataDiNascita();
        queryRequestString += "&recensioniapprovate=0&recensionirifiutate=0";
        queryRequestString += "&password=" + utente.getPassword();
        queryRequestString += "&salt=" + utente.getSalt();
        queryRequestString += "&mostra_nickname=" + utente.isMostraNickname();
        return queryRequestString;
    }

14) private String appendRequestForLogin(String username){
        return "&nickname="+username;
    }