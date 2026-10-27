// Reading works without JavaScript. This adds responsive navigation and code copying.
(() => {
  const desktop = window.matchMedia('(min-width: 761px)');
  const updateNavigation = () => {
    document.querySelectorAll('.sidebar details').forEach(details => {
      details.open = desktop.matches;
    });
  };
  updateNavigation();
  desktop.addEventListener('change', updateNavigation);
  document.querySelectorAll('.contents a').forEach(link => {
    link.addEventListener('click', () => {
      if (!desktop.matches) link.closest('details').open = false;
    });
  });
  document.querySelectorAll('.copy-button').forEach(button => {
    button.addEventListener('click', async () => {
      const code = button.closest('.code-block').querySelector('code');
      const text = code.textContent;
      const status = document.getElementById('copy-status');
      try {
        if (navigator.clipboard?.writeText) {
          try { await navigator.clipboard.writeText(text); }
          catch { copyWithSelection(text); }
        } else { copyWithSelection(text); }
        button.textContent = '복사됨';
        status.textContent = '코드를 복사했습니다.';
      } catch {
        const range = document.createRange();
        range.selectNodeContents(code);
        const selection = window.getSelection();
        selection.removeAllRanges();
        selection.addRange(range);
        button.textContent = '선택됨';
        status.textContent = '복사가 제한되어 코드를 선택했습니다. 복사 단축키를 사용하세요.';
      }
      window.setTimeout(() => { button.textContent = '복사'; }, 2200);
    });
  });
  function copyWithSelection(text) {
    const textarea = document.createElement('textarea');
    textarea.value = text;
    textarea.setAttribute('readonly', '');
    textarea.style.cssText = 'position:fixed;top:0;left:-9999px;';
    document.body.append(textarea);
    textarea.select();
    const success = document.execCommand('copy');
    textarea.remove();
    if (!success) throw new Error('Clipboard unavailable');
  }
})();
